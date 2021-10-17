<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Manage Police Records</title>
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
	<div id="errorMsg" class="alert alert-danger" role="alert"
		style="display: ${(empty error) ? 'none' : 'block'}">
		<c:out value="${error}"></c:out>
	</div>

	<form action="addEditRecord" method="post" id="addEditRecordForm"
		onsubmit="return validate();" autocomplete="off">
		<input type="hidden" name="policeReportId" value="${policeReport.id}" />

		<c:if test="${not empty policeRecordBean.id}">
			<input name="id" type="hidden" value="${policeRecordBean.id}" />
		</c:if>

		<fmt:formatDate var="formattedAccidentDate"
			value="${policeRecordBean.accidentDate.time}" pattern="MM/dd/yyyy" />

		<table id="address">
			<tr>
				<td><label>Pages: From</label></td>
				<td><input type="text" name="firstPage" id="firstPage"
					value="${policeRecordBean.firstPage}" required /></td>
				<td><label># of Pages</label></td>
				<td><select name="noOfPages" id="noOfPages">
						<c:forEach var="count" begin="1" end="${policeReport.noOfPages}">
							<c:choose>
								<c:when test="${count eq  policeRecordBean.noOfPages}">
									<option value="${count}" selected="selected">${count}</option>
								</c:when>
								<c:otherwise>
									<option value="${count}">${count}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>
				<td><label>Accident Date:</label></td>
				<td><input id="accidentDate" value="${formattedAccidentDate}"
					name="accidentDate" required></input></td>
			</tr>

			<tr>
				<td><label>Title:</label></td>
				<td colspan="2"><select name="beneficiaryTitle">
						<c:forEach items="${titles}" var="title">
							<c:choose>
								<c:when test="${title eq  policeRecordBean.beneficiaryTitle}">
									<option value="${title}" selected="selected">${title}</option>
								</c:when>
								<c:otherwise>
									<option value="${title}">${title}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>

				<td colspan="3"><input type="checkbox" name="atFault"
					id="atFault" value="true"
					${(empty policeRecordBean or policeRecordBean.atFault) ? 'checked' : ''} />
					<label for="atFault"><b>At Fault<b></label></td>
			</tr>

			<tr>
				<td><label>First Name</label></td>
				<td><input name="beneficiaryFirstName"
					value="${policeRecordBean.beneficiaryFirstName}" type="text"
					id="beneficiaryFirstName" required /></td>
				<td><label>Middle Name</label></td>
				<td><input name="beneficiaryMiddleName"
					value="${policeRecordBean.beneficiaryMiddleName}" type="text"
					id="beneficiaryMiddleName" /></td>
				<td><label>Last Name</label></td>
				<td colspan="5"><input name="beneficiaryLastName"
					value="${policeRecordBean.beneficiaryLastName}" type="text"
					id="beneficiaryLastName" required /></td>
			</tr>

			<tr>
				<td><label for="txtAddress">Address:</label></td>
				<td colspan="3"><input name="address" id="txtAddress"
					value="${policeRecordBean.address}" class="longInput" required /></td>
				<td><label>Zip:</label></td>
				<td><input type="text" name="fullZipCode" id="postal_code"
					value="${policeRecordBean.fullZipCode}" required /></td>
			</tr>

			<tr>
				<td><label>City:</label></td>
				<td><input type="text" name="cityName" id="locality"
					value="${policeRecordBean.cityName}" required /></td>
				<td><label>State:</label></td>
				<td><input type="text" name="state"
					id="administrative_area_level_1" value="${policeRecordBean.state}"
					required /></td>
				<td><label>Apt:</label></td>
				<td><input type="text" name="apartment"
					value="${policeRecordBean.apartment}" /> <span
					id="verificationFlag" hidden="true"
					style="font-weight: bold; color: Green">Verified</span></td>
			</tr>

			<input type="hidden" name="latitude"
				value="${(empty policeRecordBean.latitude) ? '0' : policeRecordBean.latitude}" />
			<input type="hidden" name="longitude"
				value="${(empty policeRecordBean.longitude) ? '0' : policeRecordBean.longitude}" />

			<tr align="center">
				<td colspan="6"><input type="submit" value="Add/Edit" /></td>
			</tr>
		</table>
	</form>

	<iframe src="listRecords/${policeReport.id}" width="100%"
		height="600px;" style="border: none;"></iframe>
</body>

<script
	src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-2.1.1.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-ui.min.js"
	type="text/javascript"></script>
<!-- <script src="https://d79i1fxsrar4t.cloudfront.net/sdk/1.4.4/smartystreets-sdk-1.4.4.min.js"></script> -->
<script
	src="https://d79i1fxsrar4t.cloudfront.net/sdk/1.1.3/smartystreets-sdk-1.1.3.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/scripts/autoCompleteAddress.js"></script>

<script type="text/javascript">
	jQuery(function($) { //on document.ready
		$("#accidentDate").datepicker({
			dateFormat : "mm/dd/yy"
		});
	});

	function validate() {
		var firstPage = $("#firstPage").val();
		var noOfPages = $("#noOfPages").val();
		var lastValidPage = $("#noOfPages option:last-child").val();
		var lastPage = parseInt(firstPage) + parseInt(noOfPages) - 1;

		if (lastPage > lastValidPage || firstPage < 1) {
			$("#errorMsg").html(
					"Please choose valid pages, the entered ones are ["
							+ firstPage + ", " + lastPage
							+ "], but the valid pages should be [1, "
							+ lastValidPage + "]");
			$("#errorMsg").css('display', 'block');
			return false;
		}

		return true;
	}
</script>
</html>