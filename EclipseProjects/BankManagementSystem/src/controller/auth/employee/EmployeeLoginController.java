package controller.auth.employee;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDao;
import helper.PasswordHelper;
import helper.Session;
import model.Employee;

@WebServlet("/employee-login")
public class EmployeeLoginController extends HttpServlet {
	
	private static final long serialVersionUID = -1591444818940832891L;

	public EmployeeLoginController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Session.clearSession(request.getSession());
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-login.jsp");
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

			EmployeeDao dao = new EmployeeDao();
			Employee employee = dao.readById(id);
			if (employee == null) {

				request.setAttribute("invalid_credentials", true);
				request.setAttribute("id", id);
				doGet(request, response);
				return;
			}
			String hashedPassword = PasswordHelper.hashPassword(password, employee.getSalt());
			if (!hashedPassword.equals(employee.getPasswordHash())) {

				request.setAttribute("invalid_credentials", true);
				request.setAttribute("id", id);
				doGet(request, response);
				return;
			}
			Session.startSession(request.getSession(), employee);
			request.getSession().setAttribute("success-message", "Login Successful!");
			response.sendRedirect("add-customer-details");
		} catch (Exception e) {
			e.printStackTrace();
			doGet(request, response);
		}
	}
}
