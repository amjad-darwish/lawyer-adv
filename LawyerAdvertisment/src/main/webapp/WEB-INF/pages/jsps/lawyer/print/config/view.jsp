<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Lawyer's Print Config</title>
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

<script
	src="${pageContext.request.contextPath}/resources/scripts/lawyer/print/config/view.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/scripts/chosen/chosen.jquery.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/chosen/chosen.min.css"
	rel="stylesheet" />
</head>
<body>
	<div class="container col-md-12">
		<c:if test="${not empty error}">
			<div class="alert alert-danger" role="alert">
				<c:out value="${error}"></c:out>
			</div>
		</c:if>

		<form action="edit" method="post" autocomplete="off">
			<input type="hidden" name="id" value="${config.id}" />

			<div class="card">
				<div class="card-header">
					<c:out value="${lawyerName}'s Print Config"></c:out>
				</div>

				<div class="card-body">
					<div class="row form-group">
						<div class="col-md-6">
							<label for="inputMiles">Miles</label> <input type="number"
								min="0" name="distance" id="inputMiles" class="form-control"
								value="${config.distance}">
						</div>

						<div class="col-md-6">
							<label for="inputZipCode">Zip Codes</label> <select
								class="chosen-select form-control" name="zipCodes"
								id="inputZipCode" size="4" multiple>
								<c:forEach var="zipInfo" items="${zipInfos}">
									<c:choose>
										<c:when test="${config['zipCodes'].contains(zipInfo.id)}">
											<option value="${zipInfo.id}" selected><c:out
													value="${zipInfo.zipCode}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${zipInfo.id}"><c:out
													value="${zipInfo.zipCode}" /></option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>


					<div class="row form-group">
						<div class="col-md-6">
							<label for="inputCity">+ City</label> <select
								class="chosen-select form-control" name="includeCitiesIds"
								id="inputCity" size="4" multiple>
								<c:forEach var="city" items="${cities}">
									<c:choose>
										<c:when
											test="${config['includeCitiesIds'].contains(city.id)}">
											<option value="${city.id}" selected><c:out
													value="${city.name}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${city.id}"><c:out
													value="${city.name}" /></option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>

						<div class="col-md-6">
							<label for="inputExcludeCity">- City</label> <select
								class="chosen-select form-control" name="excludeCitiesIds"
								id="inputExcludeCity" size="4" multiple>
								<c:forEach var="city" items="${cities}">
									<c:choose>
										<c:when
											test="${config['excludeCitiesIds'].contains(city.id)}">
											<option value="${city.id}" selected><c:out
													value="${city.name}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${city.id}"><c:out
													value="${city.name}" /></option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="row form-group">
						<div class="col-md-12">
							<label for="inputCustomersLastNames">Customer Last Names</label>
							<input type="text" name="customersLastNames"
								id="inputCustomersLastNames" class="form-control"
								value="${concatenatedCustomersLastNames}" />
						</div>
					</div>

					<div class="form-group">
						<button class="btn btn-primary" type="submit">Save</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
