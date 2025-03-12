package controller.accountmanagement;

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

@WebServlet("/account-management")
public class AccountManagementController extends HttpServlet {

	private static final long serialVersionUID = 5782944373167835514L;

	public AccountManagementController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session.checkSession(request, response, () -> {
			
			try {

				loadCustomer(request, response);
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

				Object user = request.getSession().getAttribute("user");
				if (!(user instanceof Customer)) {
					
					response.sendRedirect("customer-login");
					return;
				}
				Customer customer = (Customer) user;
				handleUpdateCustomer(request, response, customer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void loadCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Customer customer = getCustomer(request);
		if (customer == null) {

			response.sendRedirect("customer-login");
			request.getSession().setAttribute("customer", null);
		} else {

			request.getSession().setAttribute("customer", customer);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("account-management.jsp");	
		dispatcher.forward(request, response);
	}
	
	private void handleUpdateCustomer(HttpServletRequest request, HttpServletResponse response, Customer dbCustomer) throws ServletException, IOException {
		
		try {

			String firstName = request.getParameter("first-name");
			String lastName = request.getParameter("last-name");
			String accountNumber = request.getParameter("account-number");
			String ifsc = request.getParameter("ifsc");
			String aadhaarNumber = request.getParameter("aadhaar-number");
			String panNumber = request.getParameter("pan-number");
			Date dob = Date.valueOf(LocalDate.parse(request.getParameter("dob"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			String gender = request.getParameter("gender");
			String address = request.getParameter("address");
			String email = request.getParameter("email");
			String contactNumber = request.getParameter("contact-number");
			CustomerDao dao = new CustomerDao();
			Integer ssn = dbCustomer.getCustomerId();
			Double accountBalance = dbCustomer.getAccountBalance();
			String pwdHash = dbCustomer.getPasswordHash();
			String salt = dbCustomer.getSalt();
			Customer customer = new Customer(ssn, firstName, lastName, email, pwdHash, address, contactNumber, aadhaarNumber, panNumber, dob, gender, accountNumber, ifsc, accountBalance, salt);
			customer = dao.updateById(customer);
			request.getSession().setAttribute("success-message", "Details Updated!");
			request.getSession().setAttribute("customer", null);
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error-message", "Failed to Update Details!");
		}
		doGet(request, response);
	}

	private Customer getCustomer(HttpServletRequest request) throws IOException {
		
		try {

			Object user = request.getSession().getAttribute("user");
			return (Customer) user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
