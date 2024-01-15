<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="h-100 d-flex justify-content-between align-items-center">
	<%-- logo --%>
	<div>
		<h1>메모게시판</h1>
	</div>
	<%-- login --%>
	<div>
		<span>${userName}님 안녕하세요</span>
		<a href="/user/sign-out" class="mr-3">로그아웃</a>
	</div>
</div>