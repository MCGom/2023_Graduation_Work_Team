<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<meta charset="UTF-8">
</head>
<body>
<form action="signup.jsp" method="post">
<div style="text-align : center; height:300px; line-height:500px;">
<img src="/resources/images/logo.jpg" width="400px" height="150px" >
</div>
<div style="text-align : center;">
<c:if test="${not empty user.USER_ID }">  <!-- 회원일 경우 -->
로그인 유저 : ${user.USER_ID } <br/>  <!-- 로그인 유저의 아이디 출력 -->
</c:if>

<c:if test="${empty user.USER_ID }">  <!-- 비회원일 경우 -->
<input type="button" value="회원가입" onclick="location.href='user/signup'">  <!-- 회원가입 버튼 클릭시 회원가입 페이지로 전송 -->
</c:if>

<c:if test="${not empty user.USER_ID }">  <!-- 회원일 경우 -->
<input type="button" value="회원수정"  onclick="location.href='user/update'">  <!-- 회원수정 버튼 클릭시 회원정보수정 페이지로 전송 -->
</c:if>

<c:if test="${empty user.USER_ID }">  <!-- 비회원일 경우 -->
<input type="button" value="로그인"  onclick="location.href='user/login'">  <! --로그인 버튼 클릭시 로그인 페이지로 전송 -->
</c:if>
</div>
</form>
</body>
</html>