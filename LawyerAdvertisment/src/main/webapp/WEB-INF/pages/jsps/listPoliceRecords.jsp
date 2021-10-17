<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List Police Records</title>
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/mail.ico">
<link
	href="${pageContext.request.contextPath}/resources/css/lawyer-adv1.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<table style="width: 70%;">
		<thead>
			<th>Accident Date</th>
			<th>Pages</th>
			<th>Beneficiary Name</th>
			<th>Address</th>
			<th colspan="3">Actions</th>
		</thead>

		<c:forEach items="${policeRecords}" var="policeRecord">
			<tr align="center">
				<td width="10%"><fmt:formatDate
						value="${policeRecord.accidentDate.time}" pattern="MM/dd/yyyy" />
				</td>
				<td width="5%">${policeRecord.firstPage}-${policeRecord.firstPage + policeRecord.noOfPages-1}</td>
				<td width="30%">${policeRecord.beneficiaryFirstName}
					${policeRecord.beneficiaryMiddleName}
					${policeRecord.beneficiaryLastName}</td>
				<td width="40%"><c:out value="${policeRecord.printableAddress}" /></td>
				<td width="5%"><form
						action="${pageContext.request.contextPath}/policeRecord/update"
						method="post" target="_parent">
						<input type="submit" name="edit" class="button-edit" /><input
							type="hidden" name="policeRecordId" value="${policeRecord.id}" /><input
							type="hidden" name="policeReportId"
							value="${policeRecord.policeReportBean.id}" />
					</form></td>
				<td width="5%"><form
						action="${pageContext.request.contextPath}/policeRecord/view"
						method="post" target="_blank">
						<input type="submit" class="button-pdf" /><input type="hidden"
							name="policeRecordId" value="${policeRecord.id}" /><input
							type="hidden" name="policeReportId"
							value="${policeRecord.policeReportBean.id}" />
					</form></td>
				<td width="5%"><form
						action="${pageContext.request.contextPath}/policeRecord/delete"
						method="post"
						onsubmit="return confirm('Are you sure you want to delete the selected record?');">
						<input type="submit" name="delete" class="button-delete" /><input
							type="hidden" name="policeRecordId" value="${policeRecord.id}" /><input
							type="hidden" name="policeReportId"
							value="${policeRecord.policeReportBean.id}" />
					</form></td>
			</tr>
		</c:forEach>
	</table>

	<c:if test="${not empty policeRecords}">
		<jsp:include page="paginationBar.jsp">
			<jsp:param value="listRecords/${policeReportId}" name="action" />
		</jsp:include>
	</c:if>
</body>
</html>