<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<meta charset="UTF-8">
<title>View Lawyer</title>
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
	href="${pageContext.request.contextPath}/resources/css/lawyer/add.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="form-group">
		<form action="addEdit" id="addLawyer" class="container" method="post"
			enctype="multipart/form-data" autocomplete="off">
			<input type="hidden" disabled name="hasBackground"
				value="${not empty lawyer.background}" />

			<c:if test="${not empty lawyer}">
				<input type="hidden" name="id" value="${lawyer.id}" />
			</c:if>

			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${error}"></c:out>
				</div>
			</c:if>

			<div class="form-group row">
				<div class="col-md-5">
					<label for="inputName">Name</label> <input type="text" name="name"
						value="${lawyer.name}" id="inputName" class="form-control"
						${empty lawyer ? "required" : ""}
						${not empty lawyer ? "readonly" : ""}>
				</div>
			</div>

			<div class="form-group row align-items-end">
				<div class="col-md-3">
					<div class="custom-file">
						<label for="noOfPoliceReportPages">No of Pages (0 means
							All)</label> <input type="number" name="noOfPoliceReportPages" min="0"
							class="form-control" id="noOfPoliceReportPages"
							value="${lawyer.noOfPoliceReportPages}">
					</div>
				</div>

				<div class="col-md-3">
					<div class="custom-control custom-switch">
						<input type="checkbox" class="custom-control-input"
							id="usePoliceReportSwitch" name="usePoliceReport"
							${lawyer.usePoliceReport ? 'checked' : ''}> <label
							class="custom-control-label" for="usePoliceReportSwitch">Use
							Police Report</label>
					</div>
				</div>
			</div>

			<div class="form-group row align-items-end">
				<div class="col-md-3">
					<div class="custom-file">
						<input type="file" name="background" class="custom-file-input"
							id="fileBackground"> <label class="custom-file-label"
							for="fileBackground">Choose Background</label>
					</div>
				</div>

				<div class="col-md-3">
					<div class="custom-control custom-switch">
						<input type="checkbox" class="custom-control-input"
							id="useBackgroundSwitch" name="useBackground"
							${lawyer.useBackground ? 'checked' : ''}> <label
							class="custom-control-label" for="useBackgroundSwitch">Use
							Background</label>
					</div>
				</div>
			</div>

			<button class="btn btn-primary" type="submit">${empty lawyer ? "Add Lawyer" : "Update Lawyer"}</button>
		</form>
	</div>

	<div class="table-responsive container">
		<table class="table col-md-6">
			<caption>List of lawyers</caption>
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Name</th>
					<th scope="col">Offices</th>
					<th scope="col">Template</th>
					<th scope="col">Background</th>
					<th scope="col">Use Police Report</th>
					<th scope="col">No of Pages</th>
					<th scope="col" colspan="3">Actions</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${lawyers}" var="lawyer" varStatus="counter">
					<c:set var="officeCount"
						value="${(empty lawyer.offices) ? 0 : lawyer.offices.size()}" />

					<tr>
						<th scope="row">${counter.count}</th>
						<td><c:out value="${lawyer.name}"></c:out></td>
						<td>
							<form action="office/view" id="office_${lawyer.id}">
								<input type="hidden" name="lawyerId" value="${lawyer.id}" /> <input
									type="submit" value="${officeCount}" class="btn btn-link"></a>
							</form>
						</td>

						<td>
							<form action="template/view" id="template_${lawyer.id}">
								<a href="javascript:callForm('template_${lawyer.id}')">edit</a>
								<input type="hidden" name="id" value="${lawyer.template.id}" />
							</form>
						</td>

						<c:choose>
							<c:when test="${empty lawyer['background']['describableName']}">
								<td><c:out value="N/A" /></td>
							</c:when>

							<c:otherwise>
								<td>
									<form action="download" target="_blank"
										id="background_${lawyer.id}">
										<a href="javascript:callForm('background_${lawyer.id}')"><c:out
												value="${lawyer['background']['describableName']}" /></a> <input
											type="hidden" name="lawyerId" value="${lawyer.id}" />
									</form>
								</td>
							</c:otherwise>
						</c:choose>
						<td>${lawyer.usePoliceReport ? 'Yes' : 'No'}</td>
						<td>${lawyer.usePoliceReport ? (lawyer.noOfPoliceReportPages eq '0' ? 'All' : lawyer.noOfPoliceReportPages) : 'None'}</td>

						<td><form action="print/config/view" method="get">
								<input type="submit" name="editPrintConfig" class="button-print" /><input
									type="hidden" name="lawyerId" value="${lawyer.id}" />
							</form></td>

						<td><form action="view" method="post">
								<input type="submit" name="edit" class="button-edit" /><input
									type="hidden" name="lawyerId" value="${lawyer.id}" />
							</form></td>
						<td><form action="delete" method="post">
								<input type="submit" name="delete" class="button-delete" /><input
									type="hidden" name="lawyerId" value="${lawyer.id}" />
							</form></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
	</div>
</body>

<script>
	$(".custom-file-input").on(
			"change",
			function() {
				var fileName = $(this).val().split("\\").pop();
				$(this).siblings(".custom-file-label").addClass("selected")
						.html(fileName);
			});

	function callForm(formId) {
		$('#' + formId).submit();
	}

	$(document).ready(function() {
		if (!$("#usePoliceReportSwitch").is(":checked")) {
			$("#noOfPoliceReportPages").attr("disabled", true);
		}
	});

	$("#useBackgroundSwitch").on(
			"change",
			function() {
				if ($("#useBackgroundSwitch").is(":checked")
						&& $("[name='hasBackground']").val() == "false") {
					$("#fileBackground").attr("required", true);
				} else {
					$("#fileBackground").attr("required", false);
				}
			});

	$("#usePoliceReportSwitch").on("change", function() {
		if ($("#usePoliceReportSwitch").is(":checked")) {
			$("#noOfPoliceReportPages").attr("disabled", false);
		} else {
			$("#noOfPoliceReportPages").attr("disabled", true);
		}
	});
</script>

</html>