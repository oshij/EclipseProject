package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import model.Customer;
import model.Employee;
import model.Loan;
import model.Transaction;

public class Database {

	private static Connection connection;
	
	private Database() {
	}
	
	public static Connection getConnection() {
		
		try {
			
			if (connection != null && !connection.isClosed()) {

				return connection;
			}
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			connection = DriverManager.getConnection("jdbc:derby:BMS;create=true");
			createTables();
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Statement createStatement() {
		
		try {
			
			return getConnection().createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static PreparedStatement prepareStatement(String query) {
		
		try {
			
			return getConnection().prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static PreparedStatement prepareStatementWithKeys(String query) {
		
		try {
			
			return getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void destroy() {
		
		try {
			
			connection.close();
			connection = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void createTables() {
		
		createTable(Employee.getSchema());
		createTable(Customer.getSchema());
		createTable(Transaction.getSchema());
		createTable(Loan.getSchema());
	}
	
	private static void createTable(String schema) {
		
		try (Statement statement = createStatement()) {
			
			statement.executeUpdate(schema);
		} catch (SQLException e) {
			
			if (e.getSQLState().equals("X0Y32")) {
				
				Logger.getLogger(Database.class.getName()).info("Table Already Exists: " + getTableName(schema));
			} else {
				
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getTableName(String schema) {
		
		return schema.substring(schema.indexOf("TABLE ") + 6, schema.indexOf(" ("));
	}
}
