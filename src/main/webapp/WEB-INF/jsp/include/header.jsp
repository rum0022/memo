<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<div class="h-100 d-flex justify-content-between align-items-center">
	<%-- logo --%>
	<div>
		<h1>메모게시판</h1>
	</div>
	<%-- login --%>
	<div>
		<%--로그인 시 --%>
		<c:if test="${not empty userId}">
			<span>${userName}님 안녕하세요</span>
			<a href="/user/sign-out" class="mr-3">로그아웃</a>
		</c:if>	
		<%--비로그인시 --%>
		<c:if test="${empty userId}">
			<a href="/user/sign-in-view" class="mr-3 display-5">로그인</a>
		</c:if>
	</div>
</div>