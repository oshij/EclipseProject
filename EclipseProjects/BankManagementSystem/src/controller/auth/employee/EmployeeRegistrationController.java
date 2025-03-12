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

@WebServlet("/employee-registration")
public class EmployeeRegistrationController extends HttpServlet {

	private static final long serialVersionUID = 3284328624685382873L;

	public EmployeeRegistrationController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Session.clearSession(request.getSession());
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-registration.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		String employeeId = request.getParameter("employee_id");
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email= request.getParameter("email");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String contactNumber = request.getParameter("contact_number");
		try {

			String salt = PasswordHelper.getSalt();
			String hashedPassword = PasswordHelper.hashPassword(password, salt);
//			int employeeId = (int) Math.floor((System.currentTimeMillis() % 10000000.0) + 1000000.0);
			Employee employee = new Employee(
					null, 
					firstName,
					lastName, 
					email, 
					hashedPassword, 
					address, 
					contactNumber,
					salt
			);
			EmployeeDao dao = new EmployeeDao();
			employee = dao.create(employee);
			request.setAttribute("registration_success", true);
			request.setAttribute("id", employee.getEmployeeId());
			RequestDispatcher dispatcher = request.getRequestDispatcher("employee-login.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Something went wrong!");
			doGet(request, response);
		}
	}
}
