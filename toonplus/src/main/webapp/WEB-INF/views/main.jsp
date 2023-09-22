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
<div style="text-align : center;">
<img src="/resources/images/logo.jpg" width="300px", height="80px" >
<select style="float : right;" onchange="if(this.value) location.href=(this.value);"> <!-- 옵션 클릭시 현재 창에서 출력 -->
		<option value="" >마이페이지</option>
		<option value="user/update">개인정보 수정zzz</option>  <!-- 회원정보 수정 페이지 출력 -->
		<option value="">로그아웃</option>
</select>
</div>
</form>
</body>
</html>