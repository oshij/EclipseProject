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
                        <a class="nav-link" href="add-customer-details">Customer Management</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="add-transaction">Transaction Processing</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="initiate-loan-request">Loan Management</a>
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

    <div class="container">
        <ul class="nav nav-pills mt-3">
			<li class="nav-item">
		    	<button class="nav-link active" type="button" onclick="window.location.href='initiate-loan-request'">Initiate Loan Request</button>
		  	</li>
		  	<li class="nav-item">
		    	<button class="nav-link" type="button" onclick="window.location.href='update-loan-request'">Manage Loan Request</button>
		  	</li>
		</ul>
	    <div class="form-container col-10 col-md-9 col-lg-5 mx-auto">
            <form id="initiate-loan-request-form" class="needs-validation" method="post" action="initiate-loan-request" novalidate>
                <div class="mb-3">
                    <label class="form-label">Customer SSN ID</label>
                    <input type="number" name="ssn-id" class="form-control" required>
					<div class="invalid-feedback">Cannot be blank!</div>
                </div>
                <div class="row">
	                <div class="col-md-6 mb-3">
	                    <label class="form-label">Occupation</label>
	                    <input type="text" name="occupation" class="form-control" required>
   						<div class="invalid-feedback">Cannot be blank!</div>
	                </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Employer Name</label>
                        <input type="text" name="employer" class="form-control" required>
    					<div class="invalid-feedback">Cannot be blank!</div>
                    </div>
                </div>
                   <div class="mb-3">
                       <label class="form-label">Employer Address</label>
                       <input type="text" name="employer-address" class="form-control" required>
   					<div class="invalid-feedback">Cannot be blank!</div>
                   </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Loan Amount</label>
                        <input type="number" step="0.01" name="amount" class="form-control" required>
    					<div class="invalid-feedback">Enter a valid amount!</div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Duration (in years)</label>
                        <input type="number" class="form-control" name="tenure" required>
    					<div class="invalid-feedback">Enter a valid duration in years!</div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary w-100">Submit</button>
            </form>
		</div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript">
    	(() => {
    		signoutButton = document.getElementById('signout');
    		signoutButton.addEventListener('click', (event) => {
    			event.preventDefault();
    			window.location.href = 'employee-login';
    		});
    		form = document.getElementById('initiate-loan-request-form');
    		form.addEventListener('submit', (event) => {
    			event.preventDefault();
	    	    event.stopPropagation();
	    	    form.classList.add('was-validated');
	    	    if (!form.checkValidity()) {
		    	  	return;
	    	    }
	    	    form.submit();
    		});
    	})();
   	</script>
</body>
</html>