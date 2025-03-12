package controller.loanmanagement;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import dao.LoanDao;
import dao.TransactionDao;
import helper.Session;
import model.Customer;
import model.Loan;
import model.Transaction;

@WebServlet("/update-loan-request")
public class UpdateLoanRequestController extends HttpServlet {

	private static final long serialVersionUID = -7881992540551258034L;

	public UpdateLoanRequestController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session.checkSession(request, response, () -> {
			
			try {

				loadLoans(request, response);
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

				String action = request.getParameter("action");
				switch (action) {
				
					case "approve":
						approveLoan(request, response);
						break;
						
					case "delete":
						deleteLoan(request, response);
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void loadLoans(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LoanDao dao = new LoanDao();
		List<Loan> loans = dao.readAll();
		request.getSession().setAttribute("loans", loans);
		RequestDispatcher dispatcher = request.getRequestDispatcher("update-loan-request.jsp");	
		dispatcher.forward(request, response);
	}
	
	private void approveLoan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			Integer loanId = Integer.valueOf(request.getParameter("loan-id"));

			LoanDao dao = new LoanDao();
			
			Loan loan = dao.readById(loanId);

			CustomerDao customerDao = new CustomerDao();
			Customer customer = customerDao.readById(loan.getCustomerId());
			
			if (customer != null) {
				
				Boolean success = dao.updateById(loanId, true);
				if (success) {
					
					TransactionDao transactionDao = new TransactionDao();
					Transaction transaction = new Transaction(
						null, 
						customer.getCustomerId(), 
						customer.getFirstName() + " " + customer.getLastName(), 
						customer.getAccountNumber(), 
						customer.getAadhaarNumber(), 
						customer.getPanNumber(), 
						customer.getAddress(), 
						new Date(System.currentTimeMillis()), 
						customer.getContactNumber(), 
						"Loan Deposit", 
						loan.getAmount(), 
						"Credit"
					);
					transactionDao.create(transaction);
					
					customer.setAccountBalance(customer.getAccountBalance() + loan.getAmount());
					customerDao.updateById(customer);
					request.getSession().setAttribute("success-message", String.format("Loan Request Approved!\nLoan ID: %d", loanId));
					doGet(request, response);
					return;
				}				
			}
			request.getSession().setAttribute("error-message", "Failed to Approve Loan Request!");
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error-message", "Failed to Approve Loan Request!");
		}
		doGet(request, response);
	}
	
	private void deleteLoan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			Integer loanId = Integer.valueOf(request.getParameter("loan-id"));

			LoanDao dao = new LoanDao();
			
			Boolean success = dao.deleteById(loanId);
			if (success) {
				
				request.getSession().setAttribute("success-message", String.format("Loan Request Deleted!", loanId));
			} else {

				request.getSession().setAttribute("error-message", "Failed to Delete Loan Request!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error-message", "Failed to Delete Loan Request!");
		}
		doGet(request, response);
	}
}
