package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import helper.Database;
import model.Customer;

public class CustomerDao {

	public Customer create(Customer customer) {
		
		String query = "INSERT INTO customers VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = Database.prepareStatementWithKeys(query)) {
			
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPasswordHash());
			statement.setString(5, customer.getAddress());
			statement.setString(6, customer.getContactNumber());
			statement.setString(7, customer.getAadhaarNumber());
			statement.setString(8, customer.getPanNumber());
			statement.setDate(9, customer.getDob());
			statement.setString(10, customer.getGender());
			statement.setString(11, customer.getAccountNumber());
			statement.setString(12, customer.getIfsc());
			statement.setDouble(13, customer.getAccountBalance());
			statement.setString(14, customer.getSalt());
			int result = statement.executeUpdate();
			if (result == 1) {
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					
					if (resultSet.next()) {
						
						customer.setCustomerId(resultSet.getInt(1));
						return customer;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Customer> readAll() {
		
		ArrayList<Customer> customers = new ArrayList<>();
		String query = "SELECT * FROM customers";
		try (Statement statement = Database.createStatement()) {
			
			try (ResultSet resultSet = statement.executeQuery(query)) {
				
				if (resultSet.next()) {
					
					Customer customer = resolveResultSet(resultSet);
					customers.add(customer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customers;
	}

	public Customer readById(Integer customerId) {
		
		if (customerId == null) return null;
		String query = "SELECT * FROM customers WHERE customer_id = ?";
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

	public Customer readByPan(String pan) {
		
		if (pan == null) return null;
		String query = "SELECT * FROM customers WHERE pan_number LIKE ?";
		try (PreparedStatement statement = Database.prepareStatement(query)) {
			
			statement.setString(1, pan);
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
	
	public Customer updateById(Customer customer) {
		
		String query = "UPDATE customers SET first_name = ?, last_name = ?, email = ?, password_hash = ?, address = ?, contact_number = ?, aadhaar_number = ?, pan_number = ?, dob = ?, gender = ?, account_number = ?, ifsc  = ?, account_balance = ? WHERE customer_id = ?";
		try (PreparedStatement statement = Database.prepareStatement(query)) {
			
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPasswordHash());
			statement.setString(5, customer.getAddress());
			statement.setString(6, customer.getContactNumber());
			statement.setString(7, customer.getAadhaarNumber());
			statement.setString(8, customer.getPanNumber());
			statement.setDate(9, customer.getDob());
			statement.setString(10, customer.getGender());
			statement.setString(11, customer.getAccountNumber());
			statement.setString(12, customer.getIfsc());
			statement.setDouble(13, customer.getAccountBalance());
			statement.setInt(14, customer.getCustomerId());
			int result = statement.executeUpdate();
			if (result == 1) {
				
				return customer;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteById(Integer id) {
		
		String query = "DELETE FROM customers WHERE customer_id = ?";
		try (PreparedStatement statement = Database.prepareStatement(query)) {
			
			statement.setInt(1, id);
			int result = statement.executeUpdate();
			if (result == 1) {
				
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private Customer resolveResultSet(ResultSet resultSet) throws SQLException {
		
		return new Customer(
				resultSet.getInt("customer_id"),
				resultSet.getString("first_name"), 
				resultSet.getString("last_name"), 
				resultSet.getString("email"), 
				resultSet.getString("password_hash"),
				resultSet.getString("address"), 
				resultSet.getString("contact_number"),
				resultSet.getString("aadhaar_number"),
				resultSet.getString("pan_number"),
				resultSet.getDate("dob"),
				resultSet.getString("gender"),
				resultSet.getString("account_number"),
				resultSet.getString("ifsc"),
				resultSet.getDouble("account_balance"),
				resultSet.getString("salt")
		);
	}
}
