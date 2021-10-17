<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Edit Lawyer's Template</title>
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
	src="${pageContext.request.contextPath}/resources/scripts/editor/ckeditor/ckeditor.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/scripts/editor/initilizeEditor.js"></script>
<!-- <script src="https://cdn.ckeditor.com/ckeditor5/23.0.0/classic/ckeditor.js"></script> -->

<style type="text/css">
.ck-editor__editable_inline {
	height: 500px;
}
</style>

</head>
<body>
	<div class="w-75 float-right">
		<div class="card-body">
			<form method="post" id="templateForm" autocomplete="off">
				<h5 class="card-title">
					<c:out value="${template.lawyer.name}'s Template"></c:out>
				</h5>


				<input type="hidden" name="id" value="${template.id}" />

				<div class="form-group row">
					<div class="col-md-10">
						<textarea name="content" id="editor"
							class="form-control">
							<c:out value="${template.content}"></c:out>
				        </textarea>
					</div>
				</div>
			</form>

			<form action="cancel" id="cancelForm"></form>

			<div class="form-group row">
				<div class="col-md-2">
					<input type="button" value="Save" id="save"
						class="btn btn-primary btn-lg" /> <input type="button"
						value="Try" id="try" class="btn btn-secondary btn-lg" />
				</div>
				<div class="col-md-8 text-right">
					<input type="button" value="Cancel" id="cancel"
						class="btn btn-danger btn-lg" />
				</div>
			</div>

			<!-- <div class="form-group row">
				<div class="col-md-10 text-right">
					<div id="container-for-word-count"></div>
				</div>
			</div> -->
		</div>
	</div>



	<script>
		$("#save").click(function() {
			if (confirm("Would you like to save the change?")) {
				$("#templateForm").attr("action", "save");
				$("#templateForm").attr("target", "_self");
				$("#templateForm").submit();
			}
		});

		$("#try").click(function() {
			$("#templateForm").attr("action", "try");
			$("#templateForm").attr("target", "_blank");
			$("#templateForm").submit();
		});

		$("#cancel").click(function() {
			if (confirm("Would you like to cancel the change?")) {
				$("#cancelForm").submit();
			}
		});
	</script>
</body>
</html>