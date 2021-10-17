<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<script
	src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-2.1.1.js"
	type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Templates</title>
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/mail.ico">
<link
	href="${pageContext.request.contextPath}/resources/css/lawyer-adv.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<div align="center">
		<form action="uploadTemplate" method="post"
			enctype="multipart/form-data" autocomplete="off">
			<table>
				<tr align="center">
					<td><select name="lawyerId">
							<c:forEach var="lawyer" items="${lawyers}">
								<option value="${lawyer.id}">${lawyer.name}</option>
							</c:forEach>
					</select></td>
					<td><input type="file" accept="application/pdf" name="file" /></td>
					<td><input type="submit" value="upload" /></td> `
				</tr>
			</table>
		</form>


		<table style="width: 70%">
			<thead align="left">
				<th>Template</th>
				<th></th>
			</thead>

			<c:forEach items="${lawyerTemplates}" var="lawyerTemplate">
				<tr>
					<td><form action="downloadTemplate" method="post"
							target="_blank" id="template${lawyerTemplate.id}">
							<a href="javascript:callForm('#template${lawyerTemplate.id}')"><c:out
									value="${lawyerTemplate.describableName}" /></a><input
								type="hidden" name="templateId" value="${lawyerTemplate.id}" />
						</form></td>
					<td><form action="deleteTemplate" method="post">
							<input type="submit" name="delete" value="delete"
								class="button-delete" /><input type="hidden" name="templateId"
								value="${lawyerTemplate.id}" />
						</form></td>
				</tr>
			</c:forEach>
		</table>

		<form action="viewUploadTemplates" method="post"></form>
	</div>


	<script>
		function callForm(formId) {
			$(formId).submit();
		}
	</script>
</body>
</html>