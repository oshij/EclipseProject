package controller.transaction.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TransactionDao;
import helper.Session;
import model.Customer;
import model.Transaction;

@WebServlet("/customer-list-transactions")
public class ListTransactionsController extends HttpServlet {

	private static final long serialVersionUID = 1356426144185002989L;

	public ListTransactionsController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session.checkSession(request, response, () -> {
			
			try {

				handleGetTransactions(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void handleGetTransactions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			Object user = request.getSession().getAttribute("user");
			if (!(user instanceof Customer)) {
				
				response.sendRedirect("customer-login");
				return;
			}
			Customer customer = (Customer) user;
			Integer ssnId = customer.getCustomerId();
			TransactionDao dao = new TransactionDao();
			List<Transaction> transactions = dao.readAll(ssnId);
			request.getSession().setAttribute("transactions", transactions);
			RequestDispatcher dispatcher = request.getRequestDispatcher("customer-list-transactions.jsp");	
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error-message", "Failed to Load Transactions!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("customer-add-transaction.jsp");	
			dispatcher.forward(request, response);
		}
	}
}
