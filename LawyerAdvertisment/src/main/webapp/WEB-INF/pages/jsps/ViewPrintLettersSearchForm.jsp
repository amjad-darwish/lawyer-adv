<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Print-Police Records</title>
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/mail.ico">

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
<link
	href="${pageContext.request.contextPath}/resources/css/lawyer-adv.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-2.1.1.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-ui.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/scripts/printLetters.js"
	type="text/javascript"></script>
</head>
<body>
	<fmt:formatDate var="selectedFromDate" value="${fromDate.time}"
		pattern="MM/dd/yyyy" />
	<fmt:formatDate var="selectedToDate" value="${toDate.time}"
		pattern="MM/dd/yyyy" />

	<div class="container">
		<c:if test="${not empty error}">
			<div class="alert alert-danger" role="alert">
				<c:out value="${error}"></c:out>
			</div>
		</c:if>

		<form action="listUnprintedRecord" target="list" autocomplete="off">
			<!-- <div class="card"> -->
			<%-- <div class="card-header">
					<c:out value="Print Letters"></c:out>
				</div> --%>

			<!-- <div class="card-body"> -->
			<div class="row form-group">
				<div class="col-md-3">
					<label for="lawyerId">Lawyer</label> <select id="lawyerId"
						name="lawyerId" class="custom-select form-control" required>
						<option value="">--select--</option>

						<c:forEach var="lawyer" items="${lawyers}">
							<option value="${lawyer.id}">${lawyer.name}</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-md-3">
					<label for="inputZipCode">Zip Codes</label> <input type="text"
						id="inputZipCode" class="form-control" value="${zipCodes}"
						disabled>
				</div>

				<div class="col-md-3">
					<label for="includedCities">+ Cities</label> <input type="text"
						id="includedCities" class="form-control" value="${includedCities}"
						disabled>
				</div>

				<div class="col-md-3">
					<label for="excludedCities">- Cities</label> <input type="text"
						id="excludedCities" class="form-control" value="${excludedCities}"
						disabled>
				</div>
			</div>

			<div class="row form-group">
				<div class="col-md-12">
					<label for="customersLastNames">Customer Last Names</label> <input
						type="text" id="customersLastNames" class="form-control"
						value="${customersLastNames}" disabled>
				</div>
			</div>

			<div class="row form-group align-items-end">
				<div class="col-md-3">
					<label for="inputMiles">Miles</label> <input type="number" min="1"
						name="distance" id="inputMiles" class="form-control"
						value="${distance}">
				</div>

				<div class="col-md-3">
					<label for="fromDate">From:</label> <input type="text"
						name="fromDate" id="fromDate" class="form-control"
						value="${selectedFromDate}" required />
				</div>

				<div class="col-md-3">
					<label for="toDate">To:</label> <input type="text" name="toDate"
						id="toDate" class="form-control" value="${selectedToDate}"
						required />
				</div>

				<div class="col-md-3">
					<input type="submit" value="Filter" class="btn btn-primary"
						style="width: 50%" />
				</div>
			</div>
		</form>
	</div>

	<iframe name="list" width="100%" style="align: center; border: 0"
		height="350px"> </iframe>
</body>
</html>