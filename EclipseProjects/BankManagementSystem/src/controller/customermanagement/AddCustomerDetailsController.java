package controller.customermanagement;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import helper.Session;
import model.Customer;

@WebServlet("/add-customer-details")
public class AddCustomerDetailsController extends HttpServlet {

	private static final long serialVersionUID = -7671432448230038371L;

	public AddCustomerDetailsController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session.checkSession(request, response, () -> {
			
			try {

				RequestDispatcher dispatcher = request.getRequestDispatcher("add-customer-details.jsp");	
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Session.checkSession(request, response, () -> {
			
			try {

				String pan = request.getParameter("pan-number");
				Customer customer = searchCustomer(pan);
				if (customer == null) {

					handleAddCustomer(request, response);
					return;
				}
				request.getSession().setAttribute("error-message", String.format("Customer Already Exists with ID: %d!", customer.getCustomerId()));
				doGet(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private Customer searchCustomer(String pan) throws IOException {
		
		try {

			CustomerDao dao = new CustomerDao();
			return dao.readByPan(pan);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void handleAddCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			String firstName = request.getParameter("first-name");
			String lastName = request.getParameter("last-name");
			String accountNumber = request.getParameter("account-number");
			String ifsc = request.getParameter("ifsc");
			Double accountBalance = Double.parseDouble(request.getParameter("account-balance"));
			String aadhaarNumber = request.getParameter("aadhaar-number");
			String panNumber = request.getParameter("pan-number");
			Date dob = Date.valueOf(LocalDate.parse(request.getParameter("dob"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			String gender = request.getParameter("gender");
			String email = request.getParameter("email");
			String contactNumber = request.getParameter("contact-number");
			CustomerDao dao = new CustomerDao();
			Integer id = null;
			String pwdHash = null;
			String address = null;
			String salt = null;
			Customer customer = new Customer(id, firstName, lastName, email, pwdHash, address, contactNumber, aadhaarNumber, panNumber, dob, gender, accountNumber, ifsc, accountBalance, salt);
			customer = dao.create(customer);
			id = customer.getCustomerId();
			request.getSession().setAttribute("success-message", String.format("Customer Details Added!\nCustomer ID: %d", id));
			doGet(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error-message", "Failed to Add Customer Details!");
			doGet(request, response);
			return;
		}
	}
}
