<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<meta charset="UTF-8">

<script>

function checkEmail() {
	$.ajax({
		type : 'POST',
		url :  '/user/checkEmail',
		data : { EMAIL : $('#EMAIL').val()},
		dataType : "text",
		async : false,
		success : function(data) {
			if (data == 'true') { 
				alert('사용 가능한 이메일입니다.');
				$("#checkEmailVal").val("Y");
				$('#EMAIL').attr("disabled",true); 
			} else {
				alert('이미 사용하고 있는 이메일입니다.');
				$("#checkEmailVal").val("N");
			}
		}, error : function (){
			alert("에러가 발생하였습니다.");
		}
	});
}

function checkNickName() {
	$.ajax({
		type : 'POST',
		url :  '/user/checkNickName',
		data : { NICKNAME : $('#NICKNAME').val()},
		dataType : "text",
		async : false,
		success : function(data) {
			if (data == 'true') { 
				alert('사용 가능한 닉네임입니다.');
				$("#checkNickNameVal").val("Y");
				$('#NICKNAME').attr("disabled",true); 
			} else {
				alert('이미 사용하고 있는 닉네임입니다.');
				$("#checkNickNameVal").val("N");
			}
		}, error : function (){
			alert("에러가 발생하였습니다.");
		}
	});
}

function checkAction(){
	var frmSubmit = document.frmSubmit;
	

	
	if($("#checkEmailVal").val() == 'N') {
		alert('이메일 중복확인해주세요.');
		return false;
	}
	
	if($("#checkNickNameVal").val() == 'N') {
		alert('닉네임 중복확인해주세요.');
		return;
	}
	
	if($("#PASSWORD").val() != $("#PASSWORD2").val()) {
		alert('비밀번호가 일치하지 않습니다.');
		return;
	}
	
	$('#ID').attr("disabled",false); 
	$('#EMAIL').attr("disabled",false); 
	$('#NICKNAME').attr("disabled",false); 
	
	frmSubmit.submit();
	return false;
}

</script>

</head>
<body>
<form action="update" method="post" name="frmSubmit">
<input type="hidden" id="checkEmailVal" value="N"/>
<input type="hidden" id="checkNickNameVal" value="N"/> 
	<div style="text-align : center;">
	<img src="/resources/images/logo.jpg" width="300px" height="80px" >
	</div>
<table align=center>
<tr><td colspan=2 align=center height=40><b>회원정보 수정</b><td></tr>
<tr>
    <td align=right>아이디&nbsp;</td>
    <td><input type="text" name="ID" id="ID" disabled="true" value="${user.ID }"></td>
</tr>
<tr>
    <td align=right>이메일&nbsp;</td>
    <td><input type="e-mail" name="EMAIL" id="EMAIL" value="${user.EMAIL }">
       <input type="button" value="중복확인" onclick="javascript:checkEmail();"></td>
</tr>
<tr>
    <td align=right>패스워드&nbsp;</td> 
    <td><input type="password" name="PASSWORD" id="PASSWORD"></td>
</tr>
<tr> 
    <td align=right>패스워드(확인)&nbsp;</td>
    <td><input type="password" name="PASSWORD2" id="PASSWORD2"></td>
</tr>
<tr> 
    <td align=right>이름&nbsp;</td>
    <td><input type="text" name="NAME" id="NAME" required value="${user.NAME }"></td>
</tr>
<tr>
    <td align=right>닉네임&nbsp;</td>
    <td><input type="text" name="NICKNAME" id="NICKNAME" value="${user.NICKNAME }">
       <input type="button" value="중복확인" onclick="javascript:checkNickName();">
    </td></tr>
<tr>
    <td align=right>선호장르&nbsp;</td>
    <td><select name="USER_PICK1" id="USER_PICK1">
		<option value="로맨스" <c:if test="${user.USER_PICK1 eq '로맨스'}">selected="selected"</c:if>>로맨스</option>
		<option value="판타지" <c:if test="${user.USER_PICK1 eq '판타지'}">selected="selected"</c:if>>판타지</option>
		<option value="액션" <c:if test="${user.USER_PICK1 eq '액션'}">selected="selected"</c:if>>액션</option>
		<option value="일상" <c:if test="${user.USER_PICK1 eq '일상'}">selected="selected"</c:if>>일상</option>
		<option value="스릴러" <c:if test="${user.USER_PICK1 eq '스릴러'}">selected="selected"</c:if>>스릴러</option>
          </select>
          <select name="USER_PICK2" id="USER_PICK2">
		<option value="로맨스" <c:if test="${user.USER_PICK2 eq '로맨스'}">selected="selected"</c:if>>로맨스</option>
		<option value="판타지" <c:if test="${user.USER_PICK2 eq '판타지'}">selected="selected"</c:if>>판타지</option>
		<option value="액션" <c:if test="${user.USER_PICK2 eq '액션'}">selected="selected"</c:if>>액션</option>
		<option value="일상" <c:if test="${user.USER_PICK2 eq '일상'}">selected="selected"</c:if>>일상</option>
		<option value="스릴러" <c:if test="${user.USER_PICK2 eq '스릴러'}">selected="selected"</c:if>>스릴러</option>
          </select>
          <select name="USER_PICK3" id="USER_PICK3">
		<option value="로맨스" <c:if test="${user.USER_PICK3 eq '로맨스'}">selected="selected"</c:if>>로맨스</option>
		<option value="판타지" <c:if test="${user.USER_PICK3 eq '판타지'}">selected="selected"</c:if>>판타지</option>
		<option value="액션" <c:if test="${user.USER_PICK3 eq '액션'}">selected="selected"</c:if>>액션</option>
		<option value="일상" <c:if test="${user.USER_PICK3 eq '일상'}">selected="selected"</c:if>>일상</option>
		<option value="스릴러" <c:if test="${user.USER_PICK3 eq '스릴러'}">selected="selected"</c:if>>스릴러</option>
          </select>
</tr>
<tr>
    <td align=right>마이페이지 공개여부&nbsp;</td>
    <td><input type="radio" name="OPEN" value="Y" <c:if test="${user.OPEN eq 'Y'}">checked</c:if>>공개
           <input type="radio" name="OPEN" value="N" <c:if test="${user.OPEN eq 'N'}">checked</c:if>>비공개
</tr>
<tr>
    <td colspan=2 align=center height=50>
        <input type="button" value="회원정보수정하기" onclick="javascript:checkAction();">
    </td>
</tr>
</table>
</form>
</body>
</html>