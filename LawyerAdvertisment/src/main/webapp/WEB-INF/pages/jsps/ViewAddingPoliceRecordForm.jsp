<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Adding Police Records</title>
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/mail.ico">
<link
	href="${pageContext.request.contextPath}/resources/css/lawyer-adv1.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css"
	rel="stylesheet" type="text/css" />

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

</head>
<body>
	<div style="width: 100%;">
		<div style="float: left; width: 42%">
			<iframe width="100%" height="900px"
				src="${pageContext.request.contextPath}/resources/pdf-viewer/web/viewer.html?file=${pageContext.request.contextPath}/policeRecord/viewReport?policeReportId%3D${policeReport.id}"></iframe>
		</div>

		<div style="width: 20%; float: left; margin-left: 10px;">
			<label>City :</label> <label>${policeReport.city.name}</label>
		</div>

		<div align="right"
			style="width: 20%; float: right; margin-right: 10px;">
			<form action="completeReport" method="post" id="completeForm">
				<input name="policeReportId" type="hidden"
					value="${policeReport.id}" /> <input type="submit"
					value="Complete>>" class="btn btn-link">
			</form>
		</div>

		<div style="float: right; width: 58%">
			<iframe src="addEditRecordView?policeReportId=${policeReport.id}"
				width="100%" style="border: none;" height="900px"></iframe>
		</div>
	</div>
</body>
</html>