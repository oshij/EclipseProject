<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bank Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
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
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active" href="employee-registration">Registration</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="employee-login">Login</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div id="registration" class="form-container col-10 col-md-9 col-lg-5 mx-auto">
            <h2 class="text-center mb-4">Employee Registration</h2>
            <form id="registration-form" method="post" action="employee-registration" class="needs-validation" novalidate>
                <div class="mb-3" hidden>
                    <label class="form-label">Employee ID</label>
                    <input id="employee-id" type="number" name="employee_id" class="form-control" disabled required>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">First Name</label>
                        <input type="text" name="first_name" class="form-control" required>
    					<div class="invalid-feedback">Cannot be blank!</div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Last Name</label>
                        <input type="text" name="last_name" class="form-control" required>
    					<div class="invalid-feedback">Cannot be blank!</div>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email Address</label>
                    <input type="email" name="email" class="form-control" required>
    				<div class="invalid-feedback">Enter a valid email id!</div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input id="password" name="password" type="password" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,16}$" class="form-control" required>
    				<div class="invalid-feedback">
   						Password requirements:
    					<ul>
    						<li>At least 8 &amp; at most 16 characters</li>
    						<li>At least 1 capital character</li>
    						<li>At least 1 small character</li>
    						<li>At least 1 number</li>
    						<li>At least 1 symbol</li>
    					</ul>
					</div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Confirm Password</label>
                    <input id="confirm-password" name="confirm_password" type="password" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,16}$" class="form-control" required>
    				<div class="invalid-feedback">Cannot be blank!</div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Address</label>
                    <input type="text" name="address" class="form-control" required>
    				<div class="invalid-feedback">Cannot be blank!</div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Contact Number</label>
                    <input type="tel" pattern="^[0-9]{10}$" name="contact_number" class="form-control" required>
    				<div class="invalid-feedback">Must be 10 digit!</div>
                </div>
                <button type="submit" class="btn btn-primary w-100">Register</button>
        		<div class="mt-2">
        			<a href="customer-registration">Register as Customer</a>
        		</div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript">
    	(() => {
    		//employeeId = document.getElementById('employee-id');
		  	//employeeId.value = generateEmployeeId();
			registrationForm = document.getElementById('registration-form');
			registrationForm.addEventListener('submit', (event) => {
    	      	event.preventDefault();
			    event.stopPropagation();
			    if (registrationForm.classList.contains('was-validated')) {
			   		return;
			    }
			    if (registrationForm.checkValidity()) {
			   	  	if (document.getElementById('password').value !== document.getElementById('confirm-password').value) {
  	    	     		alert('Passwords don\'t match');
			  	    	return;
				  	} else {
				  		registrationForm.submit();
				  	}
		     	}
			    registrationForm.classList.add('was-validated');
    	    });
    	})();
    	//function generateEmployeeId() {
    	//    return Math.floor((Date.now() % 10000000) + 1000000);
    	//}
   	</script>
</body>
</html>