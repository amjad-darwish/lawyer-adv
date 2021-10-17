<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Download Printable Report</title>
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/mail.ico">

<script
	src="${pageContext.request.contextPath}/resources/scripts/jquery/jquery-2.1.1.js"
	type="text/javascript"></script>
</head>
<body>
	<form action="downloadPrintableReport" method="post"
		id="downloadReport">
		<a href="javascript: $('#downloadReport').submit();">Click here,
			to download the printable report</a> <input type="hidden"
			value="${groupPrintedId}" name="groupPrintedId">
	</form>
</body>
</html>