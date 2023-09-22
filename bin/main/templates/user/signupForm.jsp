<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<meta charset="UTF-8">
<script>
function checkId() {
	$.ajax({
		type : 'POST',
		url :  '/user/checkId',
		data : { ID : $('#ID').val()},
		dataType : "text",
		async : false,
		success : function(data) {
			if (data == 'true') { 
				alert('사용 가능한 아이디입니다.');
				$("#checkIdVal").val("Y");
				$('#ID').attr("disabled",true); 
			} else {
				alert('이미 사용하고 있는 아이디입니다.');
				$("#checkIdVal").val("N");
			}
		}, error : function (){
			alert("에러가 발생하였습니다.");
		}
	});
}

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
	
	if($("#checkIdVal").val() == 'N') {
		alert('아이디 중복확인해주세요.');
		return;
	} 
	
	if($("#checkEmailVal").val() == 'N') {
		alert('이메일 중복확인해주세요.');
		return;
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
<form action="signup" method="post" name="frmSubmit">
<input type="hidden" id="checkIdVal" value="N"/>
<input type="hidden" id="checkEmailVal" value="N"/>
<input type="hidden" id="checkNickNameVal" value="N"/> 
<div style="text-align : center;">
<img src="/resources/images/logo.jpg" width="300px" height="80px" >
</div>
<table align=center>
<tr><td colspan=2 align=center height=40><b>회원가입</b></td></tr>
<tr>
    <td align=right>아이디&nbsp;</td>
    <td><input type="text" name="ID" id="ID">
       <input type="button" value="중복확인" onclick="javascript:checkId();">
    </td>
    </tr>
<tr>
    <td align=right>이메일&nbsp;</td>
    <td><input type="e-mail" name="EMAIL" id="EMAIL">
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
    <td align=right>닉네임&nbsp;</td>
    <td><input type="text" name="NICKNAME" id="NICKNAME">
       <input type="button" value="중복확인" onclick="javascript:checkNickName();">
    </td>
    </tr>
<tr>
<tr>
    <td align=right>이름&nbsp;</td>
    <td><input type="text" name="NAME" id="NAME" required>
</td> 
</tr>
<tr>
    <td align=right>성별&nbsp;</td>
    <td><select name="SEX" id="SEX">
	<option value="">성별 선택</option>
	<option value="M">남</option>
	<option value="F">여</option>
	</select>
</td> 
</tr>
<tr>
    <td align=right>선호장르&nbsp;</td>
    <td><select name="USER_PICK1" id="USER_PICK1">
		<option value="선택안함">선택안함</option>
		<option value="로맨스">로맨스</option>
		<option value="판타지">판타지</option>
		<option value="액션">액션</option>
		<option value="일상">일상</option>
		<option value="스릴러">스릴러</option>
          </select>
          <select name="USER_PICK2" id="USER_PICK2">
		<option value="선택안함">선택안함</option>
		<option value="로맨스">로맨스</option>
		<option value="판타지">판타지</option>
		<option value="액션">액션</option>
		<option value="일상">일상</option>
		<option value="스릴러">스릴러</option>
          </select>
          <select name="USER_PICK3" id="USER_PICK3">
		<option value="선택안함">선택안함</option>
		<option value="로맨스">로맨스</option>
		<option value="판타지">판타지</option>
		<option value="액션">액션</option>
		<option value="일상">일상</option>
		<option value="스릴러">스릴러</option>
          </select>
          
</tr>
<tr>
    <td align=right>마이페이지 공개여부&nbsp;</td>
    <td><img src="./qm.jpg" width="5px", height="5px" title='공개합니다'>
<input type="radio" name="OPEN" value="Y">공개
           <input type="radio" name="OPEN" value="N">비공개
</tr>
<tr>
    <td colspan=2 align=center height=50>
        <input type="button" value="회원가입하기" onclick="javascript:checkAction();">
    </td>
</tr>
</table>
</form>
</body>
</html>