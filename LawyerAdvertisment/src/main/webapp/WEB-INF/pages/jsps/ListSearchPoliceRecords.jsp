<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Police Records</title>
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/mail.ico">
<link
	href="${pageContext.request.contextPath}/resources/css/lawyer-adv1.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<div align="center">
		<table style="width: 700px;">
			<thead>
				<th>Accident Date</th>
				<th>Pages</th>
				<th>Beneficiary Name</th>
				<th>Address</th>
				<th colspan="2">Actions</th>
			</thead>

			<c:forEach items="${policeRecords}" var="policeRecord">
				<tr>
					<td><fmt:formatDate value="${policeRecord.accidentDate.time}"
							pattern="MM/dd/yyyy" /></td>
					<td>${policeRecord.firstPage}-${policeRecord.firstPage + policeRecord.noOfPages-1}</td>
					<td>${policeRecord.beneficiaryFirstName}
						${policeRecord.beneficiaryMiddleName}
						${policeRecord.beneficiaryLastName}</td>
					<td>${policeRecord.address}${policeRecord.zipCode}</td>
					<td><form
							action="${pageContext.request.contextPath}/searchPoliceRecord/view"
							method="post" target="_blank">
							<input type="submit" class="button-pdf" /><input type="hidden"
								name="policeRecordId" value="${policeRecord.id}" /><input
								type="hidden" name="policeReportId"
								value="${policeRecord.policeReportBean.id}" />
						</form></td>
					<td><form
							action="${pageContext.request.contextPath}/searchPoliceRecord/delete"
							method="post">
							<input type="submit" name="delete" class="button-delete" /><input
								type="hidden" name="policeRecordId" value="${policeRecord.id}" /><input
								type="hidden" name="policeReportId"
								value="${policeRecord.policeReportBean.id}" />
						</form></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<form action="listResult" id="listForm" method="post">
		<%-- <jsp:include page="paginationBar.jsp">
			<jsp:param value="listForm" name="paginationFormId"/>
		</jsp:include> --%>

	</form>
</body>
</html>