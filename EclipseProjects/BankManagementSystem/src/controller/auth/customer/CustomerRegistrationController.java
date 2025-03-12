package controller.auth.customer;

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
import helper.PasswordHelper;
import helper.Session;
import model.Customer;

@WebServlet("/customer-registration")
public class CustomerRegistrationController extends HttpServlet {

	private static final long serialVersionUID = -4823768824539480723L;

	public CustomerRegistrationController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Session.clearSession(request.getSession());
		RequestDispatcher dispatcher = request.getRequestDispatcher("customer-registration.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String firstName = request.getParameter("first-name");
		String lastName = request.getParameter("last-name");
		String email= request.getParameter("email");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String contactNumber = request.getParameter("contact-number");
		String aadhaarNumber = request.getParameter("aadhaar-number");;
		String panNumber = request.getParameter("pan-number");
		Date dob = Date.valueOf(LocalDate.parse(request.getParameter("dob"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		String gender = request.getParameter("gender");
		String accountNumber = request.getParameter("account-number");
		String ifsc = request.getParameter("ifsc");
		Double accountBalance = 0.0;
		try {

			String salt = PasswordHelper.getSalt();
			String hashedPassword = PasswordHelper.hashPassword(password, salt);
			Customer customer = new Customer(
					null, 
					firstName,
					lastName, 
					email, 
					hashedPassword, 
					address, 
					contactNumber,
					aadhaarNumber,
					panNumber,
					dob,
					gender,
					accountNumber,
					ifsc,
					accountBalance,
					salt
			);
			CustomerDao dao = new CustomerDao();
			customer = dao.create(customer);
			request.setAttribute("registration_success", true);
			request.setAttribute("id", customer.getCustomerId());
			RequestDispatcher dispatcher = request.getRequestDispatcher("customer-login.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Something went wrong!");
			doGet(request, response);
		}
	}
}
