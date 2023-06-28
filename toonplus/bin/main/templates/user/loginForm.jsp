<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<meta charset="UTF-8">
	

<script>
function checkAction(){
	$.ajax({
		type : 'POST',
		url :  '/user/login',
		data : { ID : $('#ID').val(), PASSWORD : $("#PASSWORD").val()},
		dataType : "text",
		async : false,
		success : function(data) {
			if (data == 'true') { 
				alert('로그인되었습니다.');
				location.href="/home"; 
			} else {
				alert('로그인 실패했습니다.');
			}
		}, error : function (){
			alert("에러가 발생하였습니다.");
		}
	});
	
	return false;
}
</script>
</head>

<body>
	<form action="login" method="post" name="frmSubmit">
	<div style="text-align : center;">
	<img src="/resources/images/logo.jpg" width="300px" height="80px" >
	</div>
	<table align=center>
	<tr><td colspan=2 align=center height=40><b>로그인</b><td></tr>
	<tr>
		<td align=right>아이디&nbsp;</td> 
		<td><input type="text" name="ID" id="ID"></td>
	</tr>
	<tr>
		<td align=right>패스워드&nbsp;</td> 
		<td><input type="password" name="PASSWORD" id="PASSWORD"></td>
	</tr>
	<tr>
		<td colspan=2 align=center height=50>
			<input type="button" value="로그인하기" onclick="javascript:checkAction();">
		</td>
	</tr>
              <tr>
		<td colspan=2 align=center height=50>
			<a href=''>아이디/비밀번호 찾기</a>
		<colspan=2 align=center height=50>
			<a href='signup'>회원가입</a>
		</td>
	</tr>
	</table>
	</form>
</body>
</html>