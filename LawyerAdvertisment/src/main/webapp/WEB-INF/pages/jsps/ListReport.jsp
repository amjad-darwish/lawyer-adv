<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List Police Reports</title>
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/mail.ico">
<link
	href="${pageContext.request.contextPath}/resources/css/lawyer-adv.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<form action="listReports" id="listForm" method="post">
		<table align="center" width="50%">
			<thead>
				<tr>
					<td align="left" width="80%">Client</td>
					<td align="right" width="20%">Total</td>
				</tr>
			</thead>

			<c:forEach items="${clientCountReports}" var="clientCountReport">
				<tr>
					<td align="left"><c:out value="${clientCountReport.key}"></c:out></td>
					<td align="right"><c:out value="${clientCountReport.value}"></c:out></td>
				</tr>
			</c:forEach>
		</table>

		<%-- <jsp:include page="paginationBar.jsp">
			<jsp:param value="listForm" name="paginationFormId"/>
		</jsp:include> --%>
	</form>
</body>
</html>