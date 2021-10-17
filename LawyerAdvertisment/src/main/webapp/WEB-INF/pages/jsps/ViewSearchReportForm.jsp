<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Printed Letter Report</title>
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

<script>
	jQuery(function($) { //on document.ready
		$('#fromDate').datepicker({
			dateFormat : "mm/dd/yy"
		});
		$('#toDate').datepicker({
			dateFormat : "mm/dd/yy"
		});
	})
</script>
</head>
<body>
	<div class="form-group">
		<form action="listReports" class="container" autocomplete="off">
			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${error}"></c:out>
				</div>
			</c:if>

			<fmt:formatDate var="selectedFromDate"
				value="${searchCriteria.fromDate.time}" pattern="MM/dd/yyyy" />
			<fmt:formatDate var="selectedToDate"
				value="${searchCriteria.toDate.time}" pattern="MM/dd/yyyy" />


			<div class="form-group row align-items-end">
				<div class="col-md-3">
					<label for="fromDate">From:</label> <input type="text"
						name="fromDate" id="fromDate" class="form-control"
						value="${selectedFromDate}" />
				</div>

				<div class="col-md-3">
					<label for="toDate">To:</label> <input type="text" name="toDate"
						id="toDate" class="form-control" value="${selectedToDate}" />
				</div>

				<div class="col-md-1">
					<input type="submit" value="Search" class="btn btn-primary" />
				</div>
			</div>
		</form>
	</div>

	<div class="table-responsive container">
		<table class="table">
			<caption>Letter Count Per Lawyer</caption>
			<thead>
				<th>#</th>
				<th>Lawyer</th>
				<th class="text-right">Number of Letters</th>
			</thead>
			
			<c:forEach items="${clientCountReports}" var="clientCountReport"
				varStatus="counter">
				<tr>
					<th>${counter.count}</th>
					<td class=""><c:out value="${clientCountReport.key}" /></td>
					<td class="text-right"><c:out value="${clientCountReport.value}" /></td>
				</tr>
			</c:forEach>			
		</table>
	</div>
</body>
</html>