package model;

import java.io.Serializable;
import java.sql.Date;

public class Loan implements Serializable {
	
	private static final long serialVersionUID = 1994640711636650340L;

	private Integer loanId;
	private Integer customerId;
	private String occupation;
	private String employer;
	private String employerAddress;
	private Date date;
	private Double amount;
	private Double tenure;
	private Boolean approved;

	public Loan(
			Integer loanId,
			Integer customerId, 
			String occupation, 
			String employer, 
			String employerAddress,
			Date date, 
			Double amount,
			Double tenure,
			Boolean approved
	) {
		this.loanId= loanId;
		this.customerId = customerId;
		this.occupation = occupation;
		this.employer = employer;
		this.employerAddress = employerAddress;
		this.date = date;
		this.amount = amount;
		this.tenure = tenure;
		this.approved = approved; 
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getEmployerAddress() {
		return employerAddress;
	}

	public void setEmployerAddress(String employerAddress) {
		this.employerAddress = employerAddress;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date= date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getTenure() {
		return tenure;
	}

	public void setTenure(Double tenure) {
		this.tenure = tenure;
	}
	
	public Boolean getApproved() {
		return approved;
	}
	
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", customerId=" + customerId + ", occupation=" + occupation + ", employer="
				+ employer + ", employerAddress=" + employerAddress + ", date=" + date + ", amount=" + amount
				+ ", tenure=" + tenure + ", approved=" + approved + "]";
	}

	public static String getSchema() {
		return "CREATE TABLE loans (" +
					"loan_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
					"customer_id INT," +
				    "occupation VARCHAR(50) NOT NULL," +
				    "employer VARCHAR(50) NOT NULL," +
				    "employer_address VARCHAR(50) NOT NULL," +
				    "date DATE NOT NULL," +
				    "amount DECIMAL(31, 2) NOT NULL," +
				    "tenure DECIMAL(31, 2) NOT NULL," +
				    "approved BOOLEAN NOT NULL" +
			    ")";
	}
}
