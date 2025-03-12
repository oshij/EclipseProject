package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import helper.Database;
import model.Loan;

public class LoanDao {

	public Loan create(Loan loan) {
		
		String query = "INSERT INTO loans VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = Database.prepareStatementWithKeys(query)) {
			
			statement.setInt(1, loan.getCustomerId());
			statement.setString(2, loan.getOccupation());
			statement.setString(3, loan.getEmployer());
			statement.setString(4, loan.getEmployerAddress());
			statement.setDate(5, loan.getDate());
			statement.setDouble(6, loan.getAmount());
			statement.setDouble(7, loan.getTenure());
			statement.setBoolean(8, loan.getApproved());
			int result = statement.executeUpdate();
			if (result == 1) {
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					
					if (resultSet.next()) {
						
						loan.setLoanId(resultSet.getInt(1));
						return loan;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Loan> readAll() {
		
		ArrayList<Loan> loans = new ArrayList<>();
		String query = "SELECT * FROM loans";
		try (Statement statement = Database.createStatement()) {
			
			try (ResultSet resultSet = statement.executeQuery(query)) {
				
				while (resultSet.next()) {
					
					Loan loan = resolveResultSet(resultSet);
					loans.add(loan);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loans;
	}

	public List<Loan> readAll(Integer ssn) {
		
		ArrayList<Loan> loans = new ArrayList<>();
		String query = "SELECT * FROM loans WHERE customer_id = ?";
		try (PreparedStatement statement = Database.prepareStatement(query)) {
			
			statement.setInt(1, ssn);
			try (ResultSet resultSet = statement.executeQuery()) {
				
				while (resultSet.next()) {
					
					Loan loan = resolveResultSet(resultSet);
					loans.add(loan);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loans;
	}

	public Loan readById(Integer loanId) {
		
		if (loanId == null) return null;
		String query = "SELECT * FROM loans WHERE loan_id = ?";
		try (PreparedStatement statement = Database.prepareStatement(query)) {
			
			statement.setInt(1, loanId);
			try (ResultSet resultSet = statement.executeQuery()) {

				if (resultSet.next()) {
				
					return resolveResultSet(resultSet);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean updateById(Integer loanId, Boolean approved) {
		
		String query = "UPDATE loans SET approved = ? WHERE loan_id = ?";
		try (PreparedStatement statement = Database.prepareStatement(query)) {

			statement.setBoolean(1, approved);
			statement.setInt(2, loanId);
			int result = statement.executeUpdate();
			if (result == 1) {
				
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean deleteById(Integer loanId) {
		
		String query = "DELETE FROM loans WHERE loan_id = ?";
		try (PreparedStatement statement = Database.prepareStatement(query)) {

			statement.setInt(1, loanId);
			int result = statement.executeUpdate();
			if (result == 1) {
				
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private Loan resolveResultSet(ResultSet resultSet) throws SQLException {
		
		return new Loan(
				resultSet.getInt("loan_id"),
				resultSet.getInt("customer_id"),
				resultSet.getString("occupation"),
				resultSet.getString("employer"),
				resultSet.getString("employer_address"),
				resultSet.getDate("date"), 
				resultSet.getDouble("amount"),
				resultSet.getDouble("tenure"),
				resultSet.getBoolean("approved")
		);
	}
}
