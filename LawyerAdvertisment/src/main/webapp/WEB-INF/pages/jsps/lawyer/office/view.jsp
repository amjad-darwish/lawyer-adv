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
	href="${pageContext.request.contextPath}/resources/css/lawyer/office/view.css"
	rel="stylesheet" type="text/css" />
<script
	src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-2.1.1.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/scripts/lawyer/office/add.js"
	type="text/javascript"></script>
</head>
<body>
	<form action="add" id="addOffice" class="container" method="post"
		autocomplete="off">
		<input type="hidden" name="lawyerId" value="${lawyer.id}" />

		<div class="form-group">
			<h5 class="card-title">
				<c:out value="${lawyer.name}'s Offices"></c:out>
			</h5>

			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${error}"></c:out>
				</div>
			</c:if>

			<div class="row form-group">
				<div class="col-md-3">
					<label for="inputName">Name</label> <input type="text" name="name"
						id="inputName" class="form-control" required />
				</div>

				<div class="col-md-3">
					<label for="inputStreet">Address1</label> <input type="text"
						name="street" id="inputStreet" class="form-control" required />
				</div>

				<div class="col-md-3">
					<label for="inputSecondaryNumber">Address2</label> <input
						type="text" name="secondaryNumber" id="inputSecondaryNumber"
						class="form-control" />
				</div>
			</div>


			<div class="row form-group">
				<div class="col-md-3">
					<label for="inputState">State</label> <input type="text"
						name="state" id="inputState" class="form-control" required />
				</div>

				<div class="col-md-3">
					<label for="inputCity">City</label> <input type="text" name="city"
						id="inputCity" class="form-control" required />
				</div>

				<div class="col-md-3">
					<label for="inputZipCode">Zip code (5)</label> <input type="text"
						class="form-control" name="zipCode" id="inputZipCode" required />
				</div>
			</div>

			<div class="row form-group  align-items-end">
				<div class="col-md-3">
					<label for="inputLongitude">Longitude</label> <input type="text"
						name="longtitude" id="inputLongitude" class="form-control"
						required />
				</div>

				<div class="col-md-3">
					<label for="inputLatitude">Latitude</label> <input type="text"
						name="latitude" id="inputLatitude" class="form-control" required />
				</div>

				<div class="col-md-3">
					<button class="btn btn-secondary" id="getGeoBtn" type="button">Get
						Geometric values</button>
				</div>

			</div>

			<button class="btn btn-primary" type="submit">Add Office</button>
		</div>
	</form>

	<div class="table-responsive container">
		<table class="table col-md-6">
			<caption>List of lawyers</caption>
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Name</th>
					<th scope="col">Address1</th>
					<th scope="col">Address2</th>
					<th scope="col">State</th>
					<th scope="col">City</th>
					<th scope="col">Zip Code</th>
					<th colspan="2">Actions</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${lawyer.offices}" var="office"
					varStatus="counter">
					<tr>
						<th scope="row">${counter.count}</th>
						<td><c:out value="${office.name}"></c:out></td>
						<td><c:out value="${office['street']}"></c:out></td>
						<td><c:out value="${office['secondaryNumber']}"></c:out></td>
						<td><c:out value="${office['state']}"></c:out></td>
						<td><c:out value="${office['city']}"></c:out></td>
						<td><c:out value="${office['zipCode']}"></c:out></td>

						<td><form action="editView" method="post"
								onsubmit="return false;">
								<input type="hidden" name="lawyerId" value="${lawyer.id}" /> <input
									type="hidden" name="officeId" value="${office.id}" /> <input
									type="submit" name="edit" class="button-edit" />
							</form></td>
						<td><form action="delete" method="post">
								<input type="hidden" name="lawyerId" value="${lawyer.id}" /> <input
									type="hidden" name="officeId" value="${office.id}" /> <input
									type="submit" name="delete" class="button-delete" />
							</form></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
</body>
</html>
