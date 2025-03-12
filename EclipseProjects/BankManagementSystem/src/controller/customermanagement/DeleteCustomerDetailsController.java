package controller.customermanagement;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import helper.Session;
import model.Customer;

@WebServlet("/delete-customer-details")
public class DeleteCustomerDetailsController extends HttpServlet {

	private static final long serialVersionUID = 3043162314013801625L;

	public DeleteCustomerDetailsController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session.checkSession(request, response, () -> {
			
			try {

				RequestDispatcher dispatcher = request.getRequestDispatcher("delete-customer-details.jsp");	
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
				String ssnString = request.getParameter("delete-ssn-id");
				try {

					Integer ssn = Integer.parseInt(ssnString);
					switch (type) {
					
						case "search":
							handleSearchCustomer(request, response, ssn);
							break;
						case "delete":
							handleDeleteCustomer(request, response, ssn);
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
			request.getSession().setAttribute("delete-ssn-id", null);
			request.getSession().setAttribute("customer", null);
		} else {

			request.getSession().setAttribute("delete-ssn-id", customer.getCustomerId());
			request.getSession().setAttribute("customer", customer);
		}
		doGet(request, response);
	}
	
	private void handleDeleteCustomer(HttpServletRequest request, HttpServletResponse response, Integer ssn) throws ServletException, IOException {
		
		try {

			CustomerDao dao = new CustomerDao();
			boolean deleted = dao.deleteById(ssn);
			if (deleted) {

				request.getSession().setAttribute("success-message", "Customer Deleted!");
			} else {

				request.getSession().setAttribute("error-message", "Failed to delete customer!");
			}
			request.getSession().setAttribute("delete-ssn-id", null);
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
