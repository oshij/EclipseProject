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

@WebServlet("/update-customer-details")
public class UpdateCustomerDetailsController extends HttpServlet {

	private static final long serialVersionUID = -1669588495520857819L;

	public UpdateCustomerDetailsController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session.checkSession(request, response, () -> {
			
			try {

				RequestDispatcher dispatcher = request.getRequestDispatcher("update-customer-details.jsp");	
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

				String type = request.getParameter("type");
				String ssnString = request.getParameter("search-ssn-id");
				try {

					Integer ssn = Integer.parseInt(ssnString);
					switch (type) {
					
						case "search":
							handleSearchCustomer(request, response, ssn);
							break;
						case "update":
							handleUpdateCustomer(request, response, ssn);
							break;
					}
				} catch (NumberFormatException e) {

					request.getSession().setAttribute("error-message", "Enter a valid ID!");
					doGet(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void handleSearchCustomer(HttpServletRequest request, HttpServletResponse response, Integer ssn) throws ServletException, IOException {
		
		Customer customer = searchCustomer(ssn);
		if (customer == null) {

			request.getSession().setAttribute("error-message", "Customer Details Not Found!");
			request.getSession().setAttribute("search-ssn-id", null);
			request.getSession().setAttribute("customer", null);
		} else {

			request.getSession().setAttribute("search-ssn-id", customer.getCustomerId());
			request.getSession().setAttribute("customer", customer);
		}
		doGet(request, response);
	}
	
	private void handleUpdateCustomer(HttpServletRequest request, HttpServletResponse response, Integer ssn) throws ServletException, IOException {
		
		try {

			Customer dbCustomer = new CustomerDao().readById(ssn);
			
			String firstName = request.getParameter("first-name");
			String lastName = request.getParameter("last-name");
			String accountNumber = request.getParameter("account-number");
			String ifsc = request.getParameter("ifsc");
			String aadhaarNumber = request.getParameter("aadhaar-number");
			String panNumber = request.getParameter("pan-number");
			Date dob = Date.valueOf(LocalDate.parse(request.getParameter("dob"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			String gender = request.getParameter("gender");
			String email = request.getParameter("email");
			String contactNumber = request.getParameter("contact-number");
			CustomerDao dao = new CustomerDao();
			Double accountBalance = dbCustomer.getAccountBalance();
			String pwdHash = dbCustomer.getPasswordHash();
			String salt = dbCustomer.getSalt();
			String address = dbCustomer.getAddress();
			Customer customer = new Customer(ssn, firstName, lastName, email, pwdHash, address, contactNumber, aadhaarNumber, panNumber, dob, gender, accountNumber, ifsc, accountBalance, salt);
			customer = dao.updateById(customer);
			request.getSession().setAttribute("success-message", "Customer Details Updated!");
			request.getSession().setAttribute("search-ssn-id", null);
			request.getSession().setAttribute("customer", null);
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error-message", "Failed to Update Customer Details!");
		}
		doGet(request, response);
	}

	private Customer searchCustomer(Integer ssn) throws IOException {
		
		try {

			CustomerDao dao = new CustomerDao();
			return dao.readById(ssn);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
