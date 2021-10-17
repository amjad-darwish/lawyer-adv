<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List Police Reports</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/images/mail.ico">
<link
	href="${pageContext.request.contextPath}/resources/css/lawyer-adv.css"
	rel="stylesheet" type="text/css" />
<script
	src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-2.1.1.js"
	type="text/javascript"></script>
</head>
<body>
	<div align="center">

		<security:authorize access="hasAnyRole('ADMIN', 'DATA_ENTRY')">
			<form id="searchForm" action="search">
				<span class="small"> City: </span> <select name="cityId">
					<option selected="selected" value="">All</option>

					<c:forEach var="city" items="${cities}">
						<c:set var="describableName"
							value='${city.name.concat(" (").concat(city.county.name).concat(" County)")}' />
						<c:choose>
							<c:when test="${city.id eq selectedCityId}">
								<option selected="selected" value="${city.id}"><c:out
										value="${describableName}" /></option>
							</c:when>
							<c:otherwise>
								<option value="${city.id}"><c:out
										value="${describableName}" /></option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select> <input type="submit" value="search" />
			</form>
		</security:authorize>


		<table style="width: 80%; margin-bottom: 20px;">
			<thead>
				<tr>
					<th></th>
					<th>City</th>
					<th>Uploaded date</th>
					<th>No of pages</th>
					<th>No of Records</th>
					<security:authorize access="hasAnyRole('ADMIN', 'DATA_ENTRY')">
						<th>Finished</th>
					</security:authorize>
				</tr>
			</thead>

			<c:forEach items="${uploadedPoliceReports}"
				var="uploadedPoliceReport">
				<tr>

					<td><security:authorize access="hasRole('ADMIN')">
							<form action="delete" method="post"
								onsubmit="return confirm('Are you sure you want to delete the selected report?');">
								<input type="hidden" name="id"
									value="${uploadedPoliceReport.id}" /> <input type="hidden"
									name="cityId" value="${selectedCityId}" /> <input
									type="submit" name="delete" class="button-delete" />
							</form>
						</security:authorize></td>

					<td><c:out value="${uploadedPoliceReport.city.name}"></c:out></td>
					<td><fmt:formatDate pattern="MMM dd, yyyy (hh:mm a)"
							value="${uploadedPoliceReport.uploadedClaendar.time}" /></td>
					<td><c:out value="${uploadedPoliceReport.noOfPages}"></c:out></td>
					<td><c:out
							value="${uploadedPoliceReport.policeRecords.size()}"></c:out></td>

					<c:choose>
						<c:when test="${uploadedPoliceReport.seen}">
							<c:set value="Yes" var="seenFlag"></c:set>
						</c:when>
						<c:otherwise>
							<c:set value="No" var="seenFlag"></c:set>
						</c:otherwise>
					</c:choose>

					<security:authorize access="hasAnyRole('ADMIN', 'DATA_ENTRY')">
						<td><form id="seenForm${uploadedPoliceReport.id}"
								method="post" action="../policeRecord/viewRecords">
								<a
									href="javascript:$('#seenForm${uploadedPoliceReport.id}').submit();"><c:out
										value="${seenFlag}"></c:out></a><input type="hidden"
									name="policeReportId" value="${uploadedPoliceReport.id}" />
							</form></td>
					</security:authorize>
				</tr>
			</c:forEach>
		</table>

		<security:authorize access="hasAnyRole('ADMIN', 'DATA_ENTRY')">
			<c:if test="${not empty uploadedPoliceReports}">
				<jsp:include page="paginationBar.jsp">
					<jsp:param value="search?cityId=${selectedCityId}" name="action" />
				</jsp:include>
			</c:if>
		</security:authorize>
	</div>
</body>
</html>