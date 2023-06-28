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
<c:if test="${not empty user.ID }">  
로그인 유저 : ${user.ID } <br/>
</c:if>

<c:if test="${empty user.ID }">  
<input type="button" value="회원가입" onclick="location.href='user/signup'">
</c:if>

<c:if test="${not empty user.ID }"> 
<input type="button" value="회원수정"  onclick="location.href='user/update'"> 
</c:if>

<c:if test="${empty user.ID }">  
<input type="button" value="로그인"  onclick="location.href='user/login'">
</c:if>
</div>
</form>
</body>
</html>