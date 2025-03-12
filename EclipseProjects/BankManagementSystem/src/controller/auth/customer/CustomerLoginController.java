package controller.auth.customer;

import java.io.IOException;

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

@WebServlet("/customer-login")
public class CustomerLoginController extends HttpServlet {

	private static final long serialVersionUID = 3994220840596899104L;

	public CustomerLoginController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Session.clearSession(request.getSession());
		RequestDispatcher dispatcher = request.getRequestDispatcher("customer-login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Integer id = Integer.parseInt(request.getParameter("user_id"));
		String password = request.getParameter("password");
		if (id == null || password == null) {
			request.setAttribute("invalid_credentials", true);
			request.setAttribute("id", id);
			doGet(request, response);
			return;
		}
		try {

			CustomerDao dao = new CustomerDao();
			Customer customer = dao.readById(id);
			if (customer == null) {

				request.setAttribute("invalid_credentials", true);
				request.setAttribute("id", id);
				doGet(request, response);
				return;
			}
			String hashedPassword = PasswordHelper.hashPassword(password, customer.getSalt());
			if (!hashedPassword.equals(customer.getPasswordHash())) {

				request.setAttribute("invalid_credentials", true);
				request.setAttribute("id", id);
				doGet(request, response);
				return;
			}
			Session.startSession(request.getSession(), customer);
			request.getSession().setAttribute("success-message", "Login Successful!");
			response.sendRedirect("account-management");
		} catch (Exception e) {
			e.printStackTrace();
			doGet(request, response);
		}
	}
}
