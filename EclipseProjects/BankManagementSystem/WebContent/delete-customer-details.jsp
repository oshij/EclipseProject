<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="model.Customer" %>
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
                        <a class="nav-link active" href="add-customer-details">Customer Management</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="add-transaction">Transaction Processing</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="initiate-loan-request">Loan Management</a>
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
    	request.getSession().setAttribute("success-message", null);
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
	
	<%
		String searchSsnId = request.getSession().getAttribute("delete-ssn-id") != null ? String.valueOf(request.getSession().getAttribute("delete-ssn-id")) : "";
	%>

    <div class="container">
        <ul class="nav nav-pills mt-1">
			<li class="nav-item">
		    	<button class="nav-link" type="button" onclick="window.location.href='add-customer-details'">Add Customer Details</button>
		  	</li>
			<li class="nav-item">
		    	<button class="nav-link" type="button" onclick="window.location.href='update-customer-details'">Update Customer Details</button>
		  	</li>
			<li class="nav-item">
		    	<button class="nav-link active" type="button" onclick="window.location.href='delete-customer-details'">Delete Customer Details</button>
		  	</li>
		</ul>
		<div>
	    	<div class="form-container col-10 col-md-9 col-lg-5 mx-auto">
	    		<form class="mb-3" id="search-form" method="post" action="delete-customer-details" novalidate>
		    		<input type="hidden" name="type" value="search">
	                <div>
	                    <label class="form-label">Customer SSN ID</label>
                		<div class="row">
                			<div class="w-75">
	                    		<input id="ssn-id" type="number" class="form-control" name="delete-ssn-id" value="<%=searchSsnId%>" required>
								<div class="invalid-feedback">Enter a valid id!</div>
	                    	</div>
	                    	<button id="search" type="submit" class="btn btn-secondary w-25 h-25">
								<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
									<path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
								</svg>
							</button>
                    	</div>
	                </div>
                </form>
                <hr class="divider">
				<% 
					Customer customer = (Customer) request.getSession().getAttribute("customer");
					request.getSession().setAttribute("customer", null);
					String firstName = customer != null ? customer.getFirstName() : "";
                	String lastName = customer != null ? customer.getLastName() : "";
                	String accountNumber = customer != null ? customer.getAccountNumber() : "";
                	accountNumber = accountNumber == null ? "" : accountNumber;
                	String ifsc = customer != null ? customer.getIfsc() : "";
                	ifsc = ifsc == null ? "" : ifsc;
                	String accountBalance = customer != null ? String.format("%.2f", customer.getAccountBalance()) : "";
                	String aadhaarNumber = customer != null ? customer.getAadhaarNumber() : "";
                	String panNumber = customer != null ? customer.getPanNumber() : "";
                	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                	String dob = customer != null ? dateFormat.format(customer.getDob()) : "";
                	String gender = customer != null ? customer.getGender(): "";
                	String email = customer != null ? customer.getEmail() : "";
                	String contactNumber = customer != null ? customer.getContactNumber() : "";
				%>
	            <form id="delete-form" method="post" action="delete-customer-details" novalidate>
		    		<input type="hidden" name="type" value="delete">
		    		<input id="ssn-id" type="number" class="form-control d-none" name="delete-ssn-id" value="<%=searchSsnId%>" required>
	                <div class="row">
		                <div class="col-md-6 mb-3">
		                    <label class="form-label">First Name</label>
		                    <input type="text" class="form-control" name="first-name" value="<%= firstName %>" disabled required>
							<div class="invalid-feedback">Cannot be blank!</div>
		                </div>
	                    <div class="col-md-6 mb-3">
	                        <label class="form-label">Last Name</label>
	                        <input type="text" class="form-control" name="last-name" value="<%= lastName %>" disabled required>
	    					<div class="invalid-feedback">Cannot be blank!</div>
	                    </div>
		            </div>
	                <div class="row">
		                <div class="col-md-6 mb-3">
		                    <label class="form-label">Account Number</label>
		                    <input type="text" maxlength="12" class="form-control" name="account-number" value="<%= accountNumber %>" disabled required>
	   						<div class="invalid-feedback">Enter a valid account number!</div>
		                </div>
	                    <div class="col-md-6 mb-3">
	                        <label class="form-label">IFSC Code</label>
	                        <input type="text" pattern="^[a-zA-Z]{4}[0-9]{7}$" name="ifsc" class="form-control" value="<%= ifsc %>" disabled required>
	    					<div class="invalid-feedback">Enter a valid IFSC code!</div>
	                    </div>
	                    <div class="col-md-6 mb-3">
	                        <label class="form-label">Account Balance</label>
	                        <input type="number" step="0.01" name="account-balance" class="form-control" value="<%= accountBalance %>" disabled required>
	    					<div class="invalid-feedback">Enter a valid amount!</div>
	                    </div>
	                </div>
	                <div class="row">
	                   <div class="col-md-6 mb-3">
	                       	<label class="form-label">Aadhaar Number</label>
	                       	<input type="text" pattern="^[0-9]{12}$" name="aadhaar-number" class="form-control" value="<%= aadhaarNumber %>" disabled required>
	   						<div class="invalid-feedback">Enter a valid Aadhaar number!</div>
	                   </div>
	                   <div class="col-md-6 mb-3">
	                       	<label class="form-label">PAN Number</label>
	                       	<input type="text" pattern="^[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}$" name="pan-number" class="form-control" value="<%= panNumber %>" disabled required>
	   						<div class="invalid-feedback">Enter a valid PAN number!</div>
	                   	</div>
	                </div>
	                <div class="row">
	                    <div class="col-md-6 mb-3">
	                        <label class="form-label">Date of Birth</label>
	                        <input type="date" name="dob" class="form-control" value="<%= dob %>" disabled required>
	    					<div class="invalid-feedback">Select a valid date of date!</div>
	                    </div>
	                    <div class="col-md-6 mb-3">
		                    <label class="form-label">Gender</label>
		                    <select class="form-control form-select" name="gender" disabled required>
		                    	<option <%= "Male".equals(gender) ? "selected" : "" %>>Male</option>
		                    	<option <%= "Female".equals(gender) ? "selected" : "" %>>Female</option>
		                    </select>
		    				<div class="invalid-feedback">Select an option!</div>
		                </div>
	                   </div>
	                <div class="row">
		                <div class="col-md-6 mb-3">
		                    <label class="form-label">Email Address</label>
		                    <input type="email" name="email" class="form-control" value="<%= email %>" disabled required>
		    				<div class="invalid-feedback">Enter a valid email id!</div>
		                </div>
		                <div class="col-md-6 mb-3">
		                    <label class="form-label">Contact Number</label>
		                    <input type="tel" name="contact-number" pattern="^[0-9]{10}$" class="form-control" value="<%= contactNumber %>" disabled required>
		    				<div class="invalid-feedback">Must be 10 digit!</div>
		                </div>
	                </div>
                	<button id="delete" type="submit" class="btn btn-danger w-100">Delete</button>
	            </form>
	        </div>
	    </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript">
    	(() => {
    		signoutButton = document.getElementById('signout');
    		signoutButton.addEventListener('click', (event) => {
    			event.preventDefault();
    			window.location.href = 'login';
    		});
    		searchForm = document.getElementById('search-form');
    		deleteForm = document.getElementById('delete-form');
    		deleteButton = document.getElementById('delete');
    		searchForm.addEventListener('submit', (event) => {
    			event.preventDefault();
	    	    event.stopPropagation();
	    	    searchForm.classList.add('was-validated');
	   	      	if (!searchForm.checkValidity()) {
					return;
	   	      	}
	   	     	searchForm.submit();
    		});
    		deleteForm.addEventListener('submit', (event) => {
    			event.preventDefault();
	    	    event.stopPropagation();
	    	    searchForm.classList.add('was-validated');
	   	      	if (!searchForm.checkValidity()) {
					return;
	   	      	}
	   	     	deleteForm.submit();    			
    		});
    	})();
   	</script>
</body>
</html>