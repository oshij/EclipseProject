package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import helper.Database;
import model.Employee;

public class EmployeeDao {

	public Employee create(Employee employee) {
		
		String query = "INSERT INTO employees VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = Database.prepareStatementWithKeys(query)) {
			
			statement.setString(1, employee.getFirstName());
			statement.setString(2, employee.getLastName());
			statement.setString(3, employee.getEmail());
			statement.setString(4, employee.getPasswordHash());
			statement.setString(5, employee.getAddress());
			statement.setString(6, employee.getContactNumber());
			statement.setString(7, employee.getSalt());
			int result = statement.executeUpdate();
			if (result == 1) {
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					
					if (resultSet.next()) {
						
						employee.setEmployeeId(resultSet.getInt(1));
						return employee;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Employee> readAll() {
		
		ArrayList<Employee> employees = new ArrayList<>();
		String query = "SELECT * FROM employees";
		try (Statement statement = Database.createStatement()) {
			
			try (ResultSet resultSet = statement.executeQuery(query)) {
				
				if (resultSet.next()) {
					
					Employee employee = resolveResultSet(resultSet);
					employees.add(employee);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;
	}

	public Employee readById(Integer employeeId) {
		
		String query = "SELECT * FROM employees WHERE employee_id = ?";
		try (PreparedStatement statement = Database.prepareStatement(query)) {
			
			statement.setInt(1, employeeId);
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
	
	public Employee updateById(Employee employee) {
		
		String query = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, password_hash = ?, address = ?, contact_number = ? WHERE employee_id = ?";
		try (PreparedStatement statement = Database.prepareStatement(query)) {
			
			statement.setString(1, employee.getFirstName());
			statement.setString(2, employee.getLastName());
			statement.setString(3, employee.getEmail());
			statement.setString(4, employee.getPasswordHash());
			statement.setString(5, employee.getAddress());
			statement.setString(6, employee.getContactNumber());
			statement.setInt(7, employee.getEmployeeId());
			int result = statement.executeUpdate();
			if (result == 1) {
				
				return employee;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteById(Employee employee) {
		
		String query = "DELETE FROM employees WHERE employee_id = ?";
		try (PreparedStatement statement = Database.prepareStatement(query)) {
			
			statement.setInt(1, employee.getEmployeeId());
			int result = statement.executeUpdate();
			if (result == 1) {
				
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private Employee resolveResultSet(ResultSet resultSet) throws SQLException {
			
		return new Employee(
				resultSet.getInt("employee_id"),
				resultSet.getString("first_name"), 
				resultSet.getString("last_name"), 
				resultSet.getString("email"), 
				resultSet.getString("password_hash"),
				resultSet.getString("address"), 
				resultSet.getString("contact_number"),
				resultSet.getString("salt")
		);
	}
}
