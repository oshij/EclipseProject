<%@page import="model.Customer"%>
<%@page import="java.text.SimpleDateFormat"%>
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
                        <a class="nav-link active" href="account-management">Account Management</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="customer-add-transaction">Transaction Management</a>
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
        <div id="update" class="form-container col-10 col-md-9 col-lg-5 mx-auto">
            <h2 class="text-center mb-4">Manage Account</h2>
			<% 
				Customer customer = (Customer) request.getSession().getAttribute("customer");
				request.getSession().setAttribute("customer", null);
				String firstName = customer != null ? customer.getFirstName() : "";
               	String lastName = customer != null ? customer.getLastName() : "";
               	String accountNumber = customer != null ? customer.getAccountNumber() : "";
               	String accountBalance = customer != null ? String.format("%.2f", customer.getAccountBalance()) : "";
               	String aadhaarNumber = customer != null ? customer.getAadhaarNumber() : "";
               	String panNumber = customer != null ? customer.getPanNumber() : "";
               	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
               	String dob = customer != null ? dateFormat.format(customer.getDob()) : "";
               	String gender = customer != null ? customer.getGender(): "";
               	String email = customer != null ? customer.getEmail() : "";
               	String address = customer != null ? customer.getAddress() : "";
               	String contactNumber = customer != null ? customer.getContactNumber() : "";
			%>
            <form id="update-form" method="post" action="account-management" class="needs-validation" novalidate>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">First Name</label>
                        <input type="text" name="first-name" value="<%=firstName%>" class="form-control" required>
    					<div class="invalid-feedback">Cannot be blank!</div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Last Name</label>
                        <input type="text" name="last-name" value="<%=lastName%>" class="form-control" required>
    					<div class="invalid-feedback">Cannot be blank!</div>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email Address</label>
                    <input type="email" name="email" value="<%=email%>" class="form-control" required>
    				<div class="invalid-feedback">Enter a valid email id!</div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Address</label>
                    <input type="text" name="address" value="<%=address%>" class="form-control" required>
    				<div class="invalid-feedback">Cannot be blank!</div>
                </div>
                <div class="row">
	                <div class="col-md-6 mb-3">
	                    <label class="form-label">Email Address</label>
	                    <input type="email" name="email" value="<%=email%>" class="form-control" required>
	    				<div class="invalid-feedback">Enter a valid email id!</div>
	                </div>
	                <div class="col-md-6 mb-3">
	                    <label class="form-label">Contact Number</label>
	                    <input type="tel" name="contact-number" pattern="^[0-9]{10}$" value="<%=contactNumber%>" class="form-control" required>
	    				<div class="invalid-feedback">Must be 10 digit!</div>
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
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Date of Birth</label>
                        <input type="date" name="dob" class="form-control" value="<%=dob%>" required>
    					<div class="invalid-feedback">Select a valid date of date!</div>
                    </div>
                    <div class="col-md-6 mb-3">
	                    <label class="form-label">Gender</label>
	                    <select class="form-control form-select" name="gender" required>
	                    	<option <%= "Male".equals(gender) ? "selected" : "" %>>Male</option>
	                    	<option <%= "Female".equals(gender) ? "selected" : "" %>>Female</option>
	                    </select>
	    				<div class="invalid-feedback">Select an option!</div>
	                </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Account Balance</label>
                        <input type="number" step="0.01" name="account-balance" value="<%=accountBalance%>" class="form-control" disabled required>
    					<div class="invalid-feedback">Enter a valid amount!</div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary w-100">Update</button>
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
    		updateForm = document.getElementById('update-form');
    		updateForm.addEventListener('submit', (event) => {
    	      	event.preventDefault();
			    event.stopPropagation();
			    updateForm.classList.add('was-validated');
			    if (!updateForm.checkValidity()) {
			   	  	return;
		     	}
			    updateForm.submit();
    	    });
    	})();
   	</script>
</body>
</html>