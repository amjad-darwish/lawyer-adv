<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload Police Report</title>
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
<script
	src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-2.1.1.js"
	type="text/javascript"></script>
</head>
<body>
	<div align="center form-group">
		<form action="uploadReport" class="container" method="post"
			enctype="multipart/form-data" autocomplete="off">

			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${error}"></c:out>
				</div>
			</c:if>

			<div class="form-group row align-items-end">
				<div class="col-md-3">
					<label for="cityId">City:</label> <select name="cityId" id="cityId"
						class="custom-select" required>
						<option value="">--select--</option>
						<c:forEach var="city" items="${cities}">
							<c:set var="selected" value= "${(city.id == selectedCityId) ? 'selected' : ''}"/>
							
							<option value="${city.id}" ${selected}>${city.name.concat(" (").concat(city.county.name).concat(" County)")}</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-md-3">
					<input type="file" accept="application/pdf" name="file" id="file"
						class="custom-file-input" id="policeReportFile" required> <label
						class="custom-file-label" for="policeReportFile">Police Report</label>
				</div>

				<div class="col-md-3">
					<input type="submit" value="upload" class="btn btn-primary" />
				</div>
			</div>
		</form>
	</div>
</body>

<script type="text/javascript">
	$(".custom-file-input").on(
			"change",
			function() {
				var fileName = $(this).val().split("\\").pop();
				$(this).siblings(".custom-file-label").addClass("selected")
						.html(fileName);
			});
</script>
</html>