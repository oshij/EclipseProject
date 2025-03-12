package model;

import java.io.Serializable;
import java.sql.Date;

public class Transaction implements Serializable {
	
	private static final long serialVersionUID = -8999123131837155370L;
	
	private Integer transactionId;
	private Integer customerSsnId;
	private String customerName;
	private String accountNumber;
	private String aadhaarNumber;
	private String panNumber;
	private String address;
	private Date date;
	private String contactNumber;
	private String modeOfTransaction;
	private double amount;
	private String creditDebit;

	public Transaction(
			Integer transactionId, 
			Integer customerSsnId, 
			String customerName, 
			String accountNumber,
			String aadhaarNumber, 
			String panNumber, 
			String address, 
			Date date, 
			String contactNumber,
			String modeOfTransaction, 
			double amount, 
			String creditDebit
	) {
		this.transactionId = transactionId;
		this.customerSsnId = customerSsnId;
		this.customerName = customerName;
		this.accountNumber = accountNumber;
		this.aadhaarNumber = aadhaarNumber;
		this.panNumber = panNumber;
		this.address = address;
		this.date = date;
		this.contactNumber = contactNumber;
		this.modeOfTransaction = modeOfTransaction;
		this.amount = amount;
		this.creditDebit = creditDebit;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getCustomerSsnId() {
		return customerSsnId;
	}

	public void setCustomerSsnId(Integer customerSsnId) {
		this.customerSsnId = customerSsnId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getModeOfTransaction() {
		return modeOfTransaction;
	}

	public void setModeOfTransaction(String modeOfTransaction) {
		this.modeOfTransaction = modeOfTransaction;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCreditDebit() {
		return creditDebit;
	}

	public void setCreditDebit(String creditDebit) {
		this.creditDebit = creditDebit;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", customerSsnId=" + customerSsnId + ", customerName="
				+ customerName + ", accountNumber=" + accountNumber + ", aadhaarNumber=" + aadhaarNumber
				+ ", panNumber=" + panNumber + ", address=" + address + ", date=" + date + ", contactNumber="
				+ contactNumber + ", modeOfTransaction=" + modeOfTransaction + ", amount=" + amount + ", creditDebit="
				+ creditDebit + "]";
	}

	public static String getSchema() {
		return "CREATE TABLE transactions (" +
					"transaction_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
				    "customer_ssn INT NOT NULL," +
				    "customer_name VARCHAR(50) NOT NULL," +
				    "aadhaar_number VARCHAR(12)," +
				    "pan_number VARCHAR(10)," +
				    "address VARCHAR(100)," +
				    "date DATE," +
				    "contact_number VARCHAR(10)," +
				    "mode_of_transaction VARCHAR(20)," +
				    "amount DECIMAL(31, 2)," +
				    "credit_debit VARCHAR(10)," +
				    "account_number VARCHAR(12)" +
			    ")";
	}
}
