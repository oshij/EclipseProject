package controller.transaction.customer;

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
import dao.TransactionDao;
import helper.Session;
import model.Customer;
import model.Transaction;

@WebServlet("/customer-add-transaction")
public class AddTransactionController extends HttpServlet {

	private static final long serialVersionUID = 4886218012148951660L;

	public AddTransactionController() {
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

				handleAddTransaction(request, response);
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("customer-add-transaction.jsp");	
		dispatcher.forward(request, response);
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
	
	private void handleAddTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			Object user = request.getSession().getAttribute("user");
			if (!(user instanceof Customer)) {
				
				response.sendRedirect("customer-login");
				return;
			}
			Customer customer = (Customer) user;
			Integer ssnId = customer.getCustomerId();
			
			String customerName = request.getParameter("customer-name");
			String accountNumber = request.getParameter("account-number");
			String aadhaarNumber = customer.getAadhaarNumber();
			String panNumber = customer.getPanNumber();
			String address = customer.getAddress();
			Date date = Date.valueOf(LocalDate.parse(request.getParameter("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			String contactNumber = customer.getContactNumber();
			String modeOfTransaction = request.getParameter("mode-of-transaction");
			Double amount = Double.parseDouble(request.getParameter("amount"));
			String creditDebit = request.getParameter("credit-debit");
			TransactionDao transactionDao = new TransactionDao();
			Integer id = null;
			Transaction transaction = new Transaction(id, ssnId, customerName, accountNumber, aadhaarNumber, panNumber, address, date, contactNumber, modeOfTransaction, amount, creditDebit);

			if (transaction.getAmount() > customer.getAccountBalance() && transaction.getCreditDebit().equals("Debit")) {

				request.getSession().setAttribute("error-message", "Insufficient Balance!");
				doGet(request, response);
				return;
			} else {
				
				transaction = transactionDao.create(transaction);
				id = transaction.getTransactionId();
				
				if (transaction.getCreditDebit().equals("Debit")) {

					customer.setAccountBalance(customer.getAccountBalance() - transaction.getAmount());				
				} else {

					customer.setAccountBalance(customer.getAccountBalance() + transaction.getAmount());
				}
				CustomerDao customerDao = new CustomerDao();
				customerDao.updateById(customer);
				
				request.getSession().setAttribute("success-message", String.format("Transaction Details Added!\nTransaction ID: %d", id));
				doGet(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error-message", "Failed to Add Transaction Details!");
			doGet(request, response);
			return;
		}
	}
}
