package controller.transaction.employee;

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

@WebServlet("/add-transaction")
public class AddTransactionController extends HttpServlet {

	private static final long serialVersionUID = -7671432448230038371L;

	public AddTransactionController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session.checkSession(request, response, () -> {
			
			try {

				RequestDispatcher dispatcher = request.getRequestDispatcher("employee-add-transaction.jsp");	
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

				handleAddTransaction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void handleAddTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			Integer ssnId = Integer.parseInt(request.getParameter("ssn-id"));
			
			CustomerDao customerDao = new CustomerDao();
			Customer customer = customerDao.readById(ssnId);
			if (customer == null) {

				request.getSession().setAttribute("error-message", "Customer Does Not Exist!");
				doGet(request, response);
				return;
			}
			String customerName = request.getParameter("customer-name");
			String accountNumber = request.getParameter("account-number");
			String aadhaarNumber = request.getParameter("aadhaar-number");
			String panNumber = request.getParameter("pan-number");
			String address = request.getParameter("address");
			Date date = Date.valueOf(LocalDate.parse(request.getParameter("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			String contactNumber = request.getParameter("contact-number");
			String modeOfTransaction = request.getParameter("mode-of-transaction");
			Double amount = Double.parseDouble(request.getParameter("amount"));
			String creditDebit = request.getParameter("credit-debit");
			TransactionDao transactionDao = new TransactionDao();
			Integer id = null;
			Transaction transaction = new Transaction(id, ssnId, customerName, accountNumber, aadhaarNumber, panNumber, address, date, contactNumber, modeOfTransaction, amount, creditDebit);
			transaction = transactionDao.create(transaction);
			id = transaction.getTransactionId();
			
			if (transaction.getAmount() > customer.getAccountBalance() && transaction.getCreditDebit().equals("Debit")) {

				request.getSession().setAttribute("error-message", "Insufficient Balance!");
				doGet(request, response);
				return;
			}
			
			if (transaction.getCreditDebit().equals("Debit")) {

				customer.setAccountBalance(customer.getAccountBalance() - transaction.getAmount());				
			} else {

				customer.setAccountBalance(customer.getAccountBalance() + transaction.getAmount());
			}
			customerDao.updateById(customer);
			
			request.getSession().setAttribute("success-message", String.format("Transaction Details Added!\nTransaction ID: %d", id));
			doGet(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error-message", "Failed to Add Transaction Details!");
			doGet(request, response);
			return;
		}
	}
}
