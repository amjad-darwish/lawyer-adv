<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Police Reports</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/images/mail.ico">
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

<!-- <script src="resources/scripts/jquery/jquery-2.1.1.js"
	type="text/javascript"></script> -->
<%-- <link
	href="${pageContext.request.contextPath}/resources/css/lawyer-adv.css"
	rel="stylesheet" type="text/css" /> --%>


<style type="text/css">
.selected {
	background-color: #b5bec4;
	font-weight: bold;
}

nav {
	margin-bottom: 25px;
}
</style>
</head>
<body>
	<form target="childFrame" id="linksForm"></form>

	<nav class="navbar navbar-expand-lg navbar-light"
		style="background-color: #e3e6e8;">
		<!-- <div id="parent" class="container-fluid"> -->
		<!-- <div class="collapse navbar-collapse" id="navbarNav"> -->
		<span class="navbar-brand">Police Reports</span>

		<ul class="navbar-nav" style="width: 100%">
			<li class="nav-item"><a class="nav-link"
				href="javascript:callMethod('homePage/search')">Home</a></li>

			<security:authorize access="hasRole('ADMIN')">
				<li class="nav-item"><a class="nav-link"
					href="javascript:callMethod('policeReport/viewUploadForm')">Upload
						Files</a></li>
			</security:authorize>

			<security:authorize access="hasAnyRole('ADMIN', 'LAWYER')">
				<li class="nav-item"><a class="nav-link"
					href="javascript:callMethod('searchPoliceRecord/listResult')">Search
						Records</a></li>
			</security:authorize>

			<security:authorize access="hasRole('ADMIN')">
				<li class="nav-item"><a class="nav-link"
					href="javascript:callMethod('printLetters/viewSearchForm')">Print
						Letters</a></li>
				<li class="nav-item"><a class="nav-link"
					href="javascript:callMethod('lawyer/view')">Lawyers</a></li>
			</security:authorize>

			<security:authorize access="hasAnyRole('ADMIN', 'LAWYER')">
				<li class="nav-item"><a class="nav-link"
					href="javascript:callMethod('reports/viewSearchForm')">Report</a></li>
			</security:authorize>

			<li class="nav-item" style="margin-left: auto;"><a
				class="nav-link" href="javascript:callMethod('logout', '_parent')">Logout</a></li>
		</ul>
		<!-- </div> -->
	</nav>

	<iframe name="childFrame" width="100%" height="900px"
		style="align: center; border: 0"> </iframe>
</body>

<script type="text/javascript">
	function callMethod(action, target) {
		if (target != null) {
			$("#linksForm").attr("target", target);
		} else {
			$("#linksForm").attr("target", 'childFrame');
		}

		$("#linksForm").attr("action", action);
		$("#linksForm").submit();
	}

	$(document).ready(function() {
		$(".nav-link").click(function() {
			$(".nav-item").removeClass("active");
			$(".nav-item").removeClass("selected");
			$($(this)[0]).parent().addClass("active");
			$($(this)[0]).parent().addClass("selected");
			/* $(".nav-link").removeClass("disabled");
			$($(this)[0]).addClass("disabled"); */
		});
	});
</script>
</html>