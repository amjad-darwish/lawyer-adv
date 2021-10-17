<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Unprinted Police Record</title>
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/mail.ico">
<link
	href="${pageContext.request.contextPath}/resources/css/lawyer-adv1.css"
	rel="stylesheet" type="text/css" />

<script
	src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-2.1.1.js"
	type="text/javascript"></script>
</head>
<body>
	<div align="center">
		<form action="downloadToPrint" target="_blank" method="post"
			id="mainForm">
			<input type="hidden" name="lawyerId" value="${lawyerId}" />

			<table style="width: 700px;">
				<thead>
					<th><input type="checkbox" name="allRecords" /></th>
					<th>Accident Date</th>
					<th>Pages</th>
					<th>Beneficiary Name</th>
					<th>Address</th>
					<th>Action</th>
				</thead>
				<form action="" id="dummy"></form>
				<c:forEach items="${unprintedPoliceRecords}"
					var="unprintedPoliceRecord">
					<tr align="center">
						<td><input type="checkbox" name="recordId"
							value="${unprintedPoliceRecord.id}" /></td>
						<td><fmt:formatDate
								value="${unprintedPoliceRecord.accidentDate.time}"
								pattern="MM/dd/yyyy" /></td>
						<td>${unprintedPoliceRecord.firstPage}-${unprintedPoliceRecord.firstPage + unprintedPoliceRecord.noOfPages-1}</td>
						<td>${unprintedPoliceRecord.beneficiaryFirstName}
							${unprintedPoliceRecord.beneficiaryMiddleName}
							${unprintedPoliceRecord.beneficiaryLastName}</td>

						<td><c:out value="${unprintedPoliceRecord.printableAddress}" /></td>
						<td><form id="viewPDForm${unprintedPoliceRecord.id}"
								action="${pageContext.request.contextPath}/printLetters/view"
								method="post" target="_blank">
								<input type="button" class="button-pdf"
									onclick="javascript:$('#viewPDForm${unprintedPoliceRecord.id}').submit();" /><input
									type="hidden" name="policeRecordId"
									value="${unprintedPoliceRecord.id}" />
							</form></td>
					</tr>
				</c:forEach>
			</table>

			<input type="button" value="print" style="margin-bottom: 10px;"
				onclick="javascript: validateThenSubmit();" />
		</form>

		<c:if test="${not empty unprintedPoliceRecords}">
			<jsp:include page="paginationBar.jsp">
				<jsp:param value="getPage?lawyerId=${lawyerId}" name="action" />
			</jsp:include>
		</c:if>
	</div>
</body>

<script type="text/javascript">
	function validateThenSubmit() {
		if ($("input[name=recordId]:checked").length > 0) {
			$('#mainForm').submit();
		} else {
			alert("Please select at least one record to print then try again!");
		}
	}
	
	$("[name=allRecords]").on('change', function() {
		$("[name=recordId]").prop('checked', $("[name=allRecords]").prop("checked"));
	});
</script>
</html>