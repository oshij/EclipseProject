package model;

import java.io.Serializable;

public class Employee implements Serializable {
	
	private static final long serialVersionUID = 487364140701484041L;
	
	private Integer employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private String passwordHash;
	private String address;
	private String contactNumber;
	private String salt;
	
	public Employee(
			Integer employeeId,
			String firstName, 
			String lastName, 
			String email, 
			String passwordHash,
			String address, 
			String contactNumber,
			String salt
	) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.passwordHash = passwordHash;
		this.address = address;
		this.contactNumber = contactNumber;
		this.salt = salt;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public static String getSchema() {
		return "CREATE TABLE employees (" +
					"employee_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1000000, INCREMENT BY 1)," +
				    "first_name VARCHAR(50) NOT NULL," +
				    "last_name VARCHAR(50) NOT NULL," +
				    "email VARCHAR(100)," +
				    "password_hash VARCHAR(255) NOT NULL," +
				    "address VARCHAR(100)," +
				    "contact_number VARCHAR(10)," +
				    "salt VARCHAR(255)" +
			    ")";
	}
}
