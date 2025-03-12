<!DOCTYPE html>
<%@page import="model.Loan"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
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
		    	<button class="nav-link" type="button" onclick="window.location.href='initiate-loan-request'">Initiate Loan Request</button>
		  	</li>
		  	<li class="nav-item">
		    	<button class="nav-link active" type="button" onclick="window.location.href='update-loan-request'">Manage Loan Request</button>
		  	</li>
		</ul>
		<%
			List<?> loans = (List<?>) request.getSession().getAttribute("loans");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		%>
	    <div id="list-loans" class="mx-auto">
            <table class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col">ID</th>
			      <th scope="col">Customer ID</th>
			      <th scope="col">Date</th>
			      <th scope="col">Amount</th>
			      <th scope="col">Tenure (in years)</th>
			      <th scope="col">Status</th>
			      <th scope="col">Action</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<%
			  		for (int i = 0; i < loans.size(); i++) {
		  				Loan loan = (Loan) loans.get(i);
	  			%>
		  				<tr>
					      <td valign="middle"><%=loan.getLoanId()%></td>
					      <td valign="middle"><%=loan.getCustomerId()%></td>
					      <td valign="middle"><%=dateFormat.format(loan.getDate())%></td>
					      <td valign="middle"><%=String.format("%.1f", loan.getAmount())%></td>
					      <td valign="middle"><%=String.format("%.1f", loan.getTenure())%></td>
					      <td valign="middle"><%=loan.getApproved()?"Approved":"Not Approved"%></td>
					      <td valign="middle">
					      	<div class="row row-cols-lg-auto g-3 align-items-center">
					      		<%
					      			if (!loan.getApproved()) {
			      				%>
			      				<form method="post" action="update-loan-request">
					      			<input name="action" type="hidden" value="approve">
					      			<input name="loan-id" type="hidden" value="<%=loan.getLoanId()%>">
					      			<input class="btn btn-secondary btn-sm" type="submit" value="Approve">
					      		</form>
			      				<%
					      			}
					      		%>
					      		<form method="post" action="update-loan-request">
					      			<input name="action" type="hidden" value="delete">
					      			<input name="loan-id" type="hidden" value="<%=loan.getLoanId()%>">
					      			<input class="btn btn-danger btn-sm" type="submit" value="Delete">
					      		</form>
					      	</div>
			      		  </td>
					    </tr>
	  			<%
			  		}
			  	%>
			  </tbody>
			</table>
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
    	})();
   	</script>
</body>
</html>