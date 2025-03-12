package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import helper.Database;
import model.Transaction;

public class TransactionDao {

	public Transaction create(Transaction transaction) {
		
		String query = "INSERT INTO transactions VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = Database.prepareStatementWithKeys(query)) {
			
			statement.setInt(1, transaction.getCustomerSsnId());
			statement.setString(2, transaction.getCustomerName());
			statement.setString(3, transaction.getAadhaarNumber());
			statement.setString(4, transaction.getPanNumber());
			statement.setString(5, transaction.getAddress());
			statement.setDate(6, transaction.getDate());
			statement.setString(7, transaction.getContactNumber());
			statement.setString(8, transaction.getModeOfTransaction());
			statement.setDouble(9, transaction.getAmount());
			statement.setString(10, transaction.getCreditDebit());
			statement.setString(11, transaction.getAccountNumber());
			int result = statement.executeUpdate();
			if (result == 1) {
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					
					if (resultSet.next()) {
						
						transaction.setTransactionId(resultSet.getInt(1));
						return transaction;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Transaction> readAll(Integer ssn) {
		
		ArrayList<Transaction> transactions = new ArrayList<>();
		String query = "SELECT * FROM transactions WHERE customer_ssn = ?";
		try (PreparedStatement statement = Database.prepareStatement(query)) {
			
			statement.setInt(1, ssn);
			try (ResultSet resultSet = statement.executeQuery()) {
				
				while (resultSet.next()) {
					
					Transaction transaction = resolveResultSet(resultSet);
					transactions.add(transaction);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public Transaction readById(Integer customerId) {
		
		if (customerId == null) return null;
		String query = "SELECT * FROM transactions WHERE transaction_id = ?";
		try (PreparedStatement statement = Database.prepareStatement(query)) {
			
			statement.setInt(1, customerId);
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
	
	private Transaction resolveResultSet(ResultSet resultSet) throws SQLException {
		
		return new Transaction(
				resultSet.getInt("transaction_id"),
				resultSet.getInt("customer_ssn"),
				resultSet.getString("customer_name"),
				resultSet.getString("account_number"), 
				resultSet.getString("aadhaar_number"),
				resultSet.getString("pan_number"),
				resultSet.getString("address"),
				resultSet.getDate("date"), 
				resultSet.getString("contact_number"),
				resultSet.getString("mode_of_transaction"),
				resultSet.getDouble("amount"), 
				resultSet.getString("credit_debit")
		);
	}
}
