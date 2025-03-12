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
                        <a class="nav-link" href="employee-registration">Registration</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="employee-login">Login</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    
    <%
		Boolean showRegistrationSuccess = (Boolean) request.getAttribute("registration_success");
		Integer id = (Integer) request.getAttribute("id");
		if (showRegistrationSuccess != null && showRegistrationSuccess == true && id != null) {
	%>
	    <div id="success-alert" class="alert alert-success sticky-top" role="alert">
		  Employee Registration Successful! Your ID is: <%= id %>
		</div>
    <%
		}
	%>

    <div class="container">
        <div id="login" class="form-container col-10 col-md-9 col-lg-5 mx-auto">
            <h2 class="text-center mb-4">Employee Log In</h2>
            <form id="login-form" class="needs-validation" method="post" action="employee-login" novalidate>
                <div class="mb-3">
                    <label class="form-label">User ID</label>
                    <input id="employee-id" name="user_id" type="text" pattern="^[0-9]{7}$" class="form-control" value="<% if (id != null) {%><%=id%><%} else {%><%} %>" required>
    				<div class="invalid-feedback">Must be 7 digit!</div>
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
                <%
					Boolean invalidCredentials = (Boolean) request.getAttribute("invalid_credentials");
					if (invalidCredentials != null && invalidCredentials == true) {
				%>
				<div class="text-danger mb-3">Invalid Credentials!</div>
				<%
					}
				%>
                <button type="submit" class="btn btn-primary w-100">Log In</button>
        		<div class="mt-2">
        			<a href="customer-login">Login as Customer</a>
        		</div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript">
    	(() => {
			loginForm = document.getElementById('login-form');
    	  	loginForm.addEventListener('submit', (event) => {
	   	    	event.preventDefault();
	   	      	event.stopPropagation();
	   	   	  	loginForm.classList.add('was-validated');
	   	      	if (!loginForm.checkValidity()) {
					return;
	   	      	}
	   	   		loginForm.submit();
    	  	});
    	})();
   	</script>
</body>
</html>