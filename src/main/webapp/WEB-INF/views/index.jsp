<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 

<!DOCTYPE html>
<html>
<head>
<title>Satish Labs</title>
<link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="card">
		<c:import url="myheader.jsp" />
	</div>

	<div class="card">
		<div class="card-body">
			<div class="container" align="center">
				<h2>
					<a href='<c:url value="viewBooks"/>'> View Books</a>
					<sec:authorize access="hasRole('ADMIN') or hasRole('STOREKEEPER')">
						<br />
						<br />
						<a href="addBook"> Add Book</a>
						<br />
						<br />
						<a href="editBook"> Edit Book</a>
					</sec:authorize>
					<sec:authorize access="hasRole('ADMIN')">
						<br />
						<br />
						<a href="deleteBook"> Delete Book</a>
					</sec:authorize>
					<sec:authorize access="hasRole('CUSTOMER')">
						<br />
						<br />
						<a href="placeOrder"> Place Order</a>
					</sec:authorize>
				</h2>
			</div>
		</div>
	</div>

	<c:import url="myfooter.jsp" />

</body>
</html>