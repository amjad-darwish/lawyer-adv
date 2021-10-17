<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Police Reports</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/images/mail.ico">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
	integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
	crossorigin="anonymous"></script>
<script
	src="${pageContext.request.contextPath}/resources/scripts/login.js"></script>
</head>
<body>
	<div class="container"
		style="position: absolute; left: 32%; top: 25%;">
		
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
				<div class="text-center alert alert-danger" role="alert">
					<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"></c:out>
				</div>
			</c:if>
					
			<div class="card">
				<div class="card-header">Login</div>
				<div class="card-body">
					<!-- Start form -->
					<form action="<%=request.getContextPath()%>/loginApp" method="POST" autocomplete="off">
						<div class="form-group">
							<label for="username">User Name</label> <input type="text"
								name="username" class="form-control" id="username"
								placeholder="User Name">
						</div>
						<div class="form-group">
							<label for="password">Password</label> <input type="password"
								class="form-control" name="password" id="password"
								placeholder="Password">
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-primary">Login</button>
							<button class="btn btn-info" type="button" name="showpassword"
								id="showpassword" value="Show Password">Show password</button>
						</div>
						<%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
					</form>
					<!-- End form -->
				</div>
			</div>
		</div>
	</div>
</body>
</html>
