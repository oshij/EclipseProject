package controller.loanmanagement;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import dao.LoanDao;
import helper.Session;
import model.Customer;
import model.Loan;

@WebServlet("/initiate-loan-request")
public class InitiateLoanRequestController extends HttpServlet {

	private static final long serialVersionUID = 795814394054089546L;

	public InitiateLoanRequestController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session.checkSession(request, response, () -> {
			
			try {

				RequestDispatcher dispatcher = request.getRequestDispatcher("initiate-loan-request.jsp");	
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

				handleInitiateLoanRequest(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void handleInitiateLoanRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			Integer ssnId = Integer.valueOf(request.getParameter("ssn-id"));
			
			CustomerDao customerDao = new CustomerDao();
			Customer customer = customerDao.readById(ssnId);
			if (customer == null) {
				
				request.getSession().setAttribute("error-message", "Failed to Initiate Loan Request!\nCustomer Does Not Exist!");
				doGet(request, response);
				return;
			}
			
			String occupation = request.getParameter("occupation");
			String employer = request.getParameter("employer");
			String employerAddress = request.getParameter("employer-address");
			Double amount = Double.parseDouble(request.getParameter("amount"));
			Double tenureInYears = Double.parseDouble(request.getParameter("tenure"));
			Date date = new Date(System.currentTimeMillis());
			Boolean approved = false;

			LoanDao dao = new LoanDao();
			
			Loan loan = new Loan(null, ssnId, occupation, employer, employerAddress, date, amount, tenureInYears, approved);
			loan = dao.create(loan);
			Integer id = loan.getLoanId();
			request.getSession().setAttribute("success-message", String.format("Loan Request Initiated!\nLoan ID: %d", id));
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error-message", "Failed to Initiate Loan Request!");
		}
		doGet(request, response);
	}
}
