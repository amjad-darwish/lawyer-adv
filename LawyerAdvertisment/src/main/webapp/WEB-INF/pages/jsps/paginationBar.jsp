<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

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

<c:set var="currentPage" value="${currentPage}" />
<c:set var="firstPage" value="${firstPage}" />
<c:set var="lastPage" value="${lastPage}" />
<c:set var="noOfPages" value="${noOfPages}" />
<c:set var="action" value="${param.action}" />
<c:set var="target" value="${(empty target) ? '_self' : target}" />

	<c:choose>
		<c:when test="${fn:contains(action, '?')}">
			<c:set var="action" value="${action}&" />		
		</c:when>
		<c:otherwise>
			<c:set var="action" value="${action}?" />
		</c:otherwise>
	</c:choose>
	
	
	<c:if test="${currentPage eq 1}">
		<c:set var="beginDisabled" value="disabled" />
	</c:if>

	<c:if test="${currentPage eq noOfPages}">
		<c:set var="endDisabled" value="disabled" />
	</c:if>
	<nav aria-label="Pagination Page">
		<ul class="pagination" style="display: flex;justify-content: center;">
			<li class="page-item ${beginDisabled}"><a class="page-link"
				href="${action}p=1" target="${target}" aria-label="First"><span
					aria-hidden="true">&laquo;</span> <span class="sr-only">First</span></a></li>

			<c:forEach begin="${firstPage}" end="${lastPage}" var="page">
				<c:choose>
					<c:when test="${currentPage eq page}">
						<li class="page-item active"><a class="page-link"
							href="#" target="${target}">${page} <span
								class="sr-only">(current)</span>
						</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link"
							href="${action}p=${page}" target="${target}">${page}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<li class="page-item ${endDisabled}"><a class="page-link"
				href="${action}p=${noOfPages}" target="${target}" aria-label="Last">
					<span aria-hidden="true">&raquo;</span> <span class="sr-only">Last</span>
			</a></li>

		</ul>
	</nav>
