<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Transaction"%>
<%@page import="java.util.List"%>
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
		    	<button class="nav-link" type="button" onclick="window.location.href='customer-add-transaction'">Add Transaction</button>
		  	</li>
			<li class="nav-item">
		    	<button class="nav-link active" type="button" onclick="window.location.href='customer-list-transactions'">List Transactions</button>
		  	</li>
		</ul>
		<%
			List<?> transactions = (List<?>) request.getSession().getAttribute("transactions");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		%>
        <div id="list-transactions" class="mx-auto">
            <table class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col">ID</th>
			      <th scope="col">Date</th>
			      <th scope="col">Credit/Debit</th>
			      <th scope="col">Amount</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<%
			  		for (int i = 0; i < transactions.size(); i++) {
		  				Transaction transaction = (Transaction) transactions.get(i);
	  			%>
		  				<tr>
					      <td scope="row"><%=transaction.getTransactionId()%></td>
					      <td><%=dateFormat.format(transaction.getDate())%></td>
					      <td><%=transaction.getCreditDebit()%></td>
					      <td><%=transaction.getAmount()%></td>
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
				window.location.href = 'customer-login';
			});
		})();
   	</script>
</body>
</html>