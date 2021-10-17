<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Record</title>
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
		$('#inputDateOA').datepicker({
			dateFormat : "mm/dd/yy"
		});
	})
</script>

</head>
<body>
	<div class="form-group">
		<form action="listResult" class="container" autocomplete="off">
			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${error}"></c:out>
				</div>
			</c:if>

			<fmt:formatDate var="selectedDate" value="${searchBean.dateOA.time}"
				pattern="MM/dd/yyyy" />

			<div class="form-group row align-items-end">
				<div class="col-md-3">
					<label for="inputFirstName">First Name:</label> <input type="text"
						name="firstName" id="inputFirstName" class="form-control"
						value="${searchBean.firstName}" />
				</div>

				<div class="col-md-3">
					<label for="inputLastName">Last Name:</label> <input type="text"
						name="lastName" id="inputLastName" class="form-control"
						value="${searchBean.lastName}" />
				</div>

				<div class="col-md-3">
					<label for="inputDateOA">DAO:</label> <input type="text"
						name="dateOA" id="inputDateOA" class="form-control"
						value="${selectedDate}" />
				</div>

				<div class="col-md-1">
					<input type="submit" value="search" class="btn btn-primary" />
				</div>
			</div>
		</form>
	</div>

	<c:if test="${not empty policeRecords}">
		<div class="table-responsive container">
			<table class="table col-md-10">
				<caption>Police Records</caption>
				<thead>
					<th scope="col">#</th>
					<th>Accident Date</th>
					<th>Pages</th>
					<th>Beneficiary Name</th>
					<th>Address</th>
					<th>Actions</th>
				</thead>

				<c:forEach items="${policeRecords}" var="policeRecord"
					varStatus="counter">
					<tr>
						<th scope="row">${counter.count}</th>
						<td><fmt:formatDate value="${policeRecord.accidentDate.time}"
								pattern="MM/dd/yyyy" /></td>
						<td>${policeRecord.firstPage}-${policeRecord.firstPage + policeRecord.noOfPages-1}</td>
						<td>${policeRecord.beneficiaryFirstName}
							${policeRecord.beneficiaryMiddleName}
							${policeRecord.beneficiaryLastName}</td>
						<td>${policeRecord.printableAddress}</td>
						<td style="text-align: center;"><form
								action="${pageContext.request.contextPath}/searchPoliceRecord/view"
								target="_blank">
								<input type="submit" class="button-pdf" /><input type="hidden"
									name="policeRecordId" value="${policeRecord.id}" />
							</form></td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<jsp:include page="paginationBar.jsp">
			<jsp:param
				value="listResult?firstName=${searchBean.firstName}&lastName=${searchBean.lastName}&dateOA=${selectedDate}"
				name="action" />
		</jsp:include>
	</c:if>
	
	<c:if test="${empty policeRecords}">
		<div align="center" style="margin-top: 20%">
			<div  style="width: 50%; text-align: center; display: inline-block;" class="alert alert-secondary" role="alert">
				No Data Found
			</div>
		</div>
	</c:if>
</body>
</html>