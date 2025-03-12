<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bank Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="./common.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary sticky-top">
        <div class="container">
            <a class="navbar-brand" href="#">Bank Management System</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="account-management">Account Management</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="customer-add-transaction">Transaction Management</a>
                    </li>
                </ul>
                <div class="d-flex">
               		<button id="signout" class="btn btn-primary">
               			<i class="bi bi-box-arrow-right"></i>
					</button>
                </div>
            </div>
        </div>
    </nav>
    
    <%
    	String successMessage = (String) request.getSession().getAttribute("success-message");
    	request.getSession().setAttribute("success-message", null);
		if (successMessage != null) {
	%>
		<div id="success-alert" class="alert alert-success sticky-top" role="alert">
			<%=successMessage%>
		</div>
		<script type="text/javascript">
			setTimeout(() => {
				document.getElementById("success-alert").style.display = 'none';
			}, 3000);
		</script>
    <%
		}
		String errorMessage = (String) request.getSession().getAttribute("error-message");
    	request.getSession().setAttribute("error-message", null);
		if (errorMessage != null) {
	%>
		<div id="error-alert" class="alert alert-danger sticky-top" role="alert">
			<%=errorMessage%>
		</div>
		<script type="text/javascript">
			setTimeout(() => {
				document.getElementById("error-alert").style.display = 'none';
			}, 3000);
		</script>
    <%
		}
	%>

    <div class="container">
        <ul class="nav nav-pills mt-1">
			<li class="nav-item">
		    	<button class="nav-link active" type="button" onclick="window.location.href='customer-add-transaction'">Add Transaction</button>
		  	</li>
			<li class="nav-item">
		    	<button class="nav-link" type="button" onclick="window.location.href='customer-list-transactions'">List Transactions</button>
		  	</li>
		</ul>
        <div id="add-transaction" class="form-container col-10 col-md-9 col-lg-5 mx-auto">
            <h2 class="text-center mb-4">Add Transaction</h2>
            <% 
				Customer customer = (Customer) request.getSession().getAttribute("customer");
				String ssnId = customer != null ? String.valueOf(customer.getCustomerId()) : "";
				String firstName = customer != null ? customer.getFirstName() : "";
               	String lastName = customer != null ? customer.getLastName() : "";
               	String accountNumber = customer != null ? customer.getAccountNumber() : "";
               	if (accountNumber == null) accountNumber = "";
               	String aadhaarNumber = customer != null ? customer.getAadhaarNumber() : "";
               	String panNumber = customer != null ? customer.getPanNumber() : "";
               	String address = customer != null ? customer.getAddress() : "";
               	String contactNumber = customer != null ? customer.getContactNumber() : "";
			%>
            <form id="add-form" class="needs-validation" method="post" action="customer-add-transaction" novalidate>
                <div class="row">
	                <div class="col-md-6 mb-3">
	                    <label class="form-label">Transaction ID</label>
	                    <input id="transaction-id" type="number" class="form-control" name="transaction-id" disabled required>
   						<div class="invalid-feedback">Cannot be blank!</div>
	                </div>
	                <div class="col-md-6 mb-3">
	                    <label class="form-label">Customer SSN ID</label>
	                    <input type="number" class="form-control" name="ssn-id" value="<%=ssnId%>" required>
						<div class="invalid-feedback">Cannot be blank!</div>
	                </div>
	            </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Customer Name</label>
                        <input type="text" class="form-control" name="customer-name" value="<%=firstName+" "+lastName%>" required>
    					<div class="invalid-feedback">Cannot be blank!</div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Account Number</label>
	                    <input type="text" maxlength="12" class="form-control" name="account-number" value="<%=accountNumber%>" required>
    					<div class="invalid-feedback">Cannot be blank!</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Aadhaar Number</label>
                       	<input type="text" pattern="^[0-9]{12}$" name="aadhaar-number" value="<%=aadhaarNumber%>" class="form-control" required>
    					<div class="invalid-feedback">Enter a valid Aadhaar number!</div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">PAN Number</label>
                       	<input type="text" pattern="^[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}$" name="pan-number" value="<%=panNumber%>" class="form-control" required>
    					<div class="invalid-feedback">Enter a valid PAN number!</div>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Address</label>
                    <input type="text" class="form-control" name="address" value="<%=address%>" required>
   					<div class="invalid-feedback">Cannot be blank!</div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Date</label>
                        <input type="date" class="form-control" name="date" required>
    					<div class="invalid-feedback">Select a date!</div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Contact Number</label>
	                    <input type="tel" pattern="^[0-9]{10}$" class="form-control" name="contact-number" value="<%=contactNumber%>" required>
		   				<div class="invalid-feedback">Must be 10 digit!</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
	                    <label class="form-label">Mode of Transaction</label>
	                    <select class="form-control form-select" name="mode-of-transaction" required>
	                    	<option>UPI</option>
	                    	<option>Net Banking</option>
	                    	<option>Cheque</option>
	                    	<option>Credit Card</option>
	                    	<option>Debit Card</option>
	                    	<option>Cash</option>
	                    </select>
	    				<div class="invalid-feedback">Cannot be blank!</div>
	                </div>
                    <div class="col-md-6 mb-3">
	                    <label class="form-label">Amount of Transaction</label>
	                    <input type="number" step="0.01" class="form-control" name="amount" required>
	    				<div class="invalid-feedback">Enter a valid amount!</div>
	                </div>
                    <div class="col-md-6 mb-3">
	                    <label class="form-label">Credit/Debit</label>
	                    <select class="form-control form-select" name="credit-debit" required>
	                    	<option>Credit</option>
	                    	<option>Debit</option>
	                    </select>
	    				<div class="invalid-feedback">Select an option!</div>
	                </div>
                </div>
                <button type="submit" class="btn btn-primary w-100">Add</button>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript">
		(() => {
			signoutButton = document.getElementById('signout');
			signoutButton.addEventListener('click', (event) => {
				event.preventDefault();
				window.location.href = 'customer-login';
			});
			addForm = document.getElementById('add-form');
			addForm.addEventListener('submit', (event) => {
				event.preventDefault();
	    	    event.stopPropagation();
	    	    addForm.classList.add('was-validated');
	   	      	if (!addForm.checkValidity()) {
					return;
	   	      	}
	   	     	addForm.submit();
			});
		})();
   	</script>
</body>
</html>