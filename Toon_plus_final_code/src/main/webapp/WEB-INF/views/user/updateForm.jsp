<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<meta charset="UTF-8">

<script>

function checkEmail() {  //이메일 유효성 검사
    var emailValue = $('#USER_MAIL').val();

    if (!isValidEmail(emailValue)) {
        alert('올바른 이메일 형식이 아닙니다.');  //일반적인 이메일 형식이 아닐 경우 메시지 출력
        return;
    }

	$.ajax({  //이메일 중복여부 검사
		type : 'POST',
		url :  '/user/checkEmail',
		data : { USER_MAIL : $('#USER_MAIL').val()},
		dataType : "text",
		async : false,
		success : function(data) {
			if (data == 'true') { 
				alert('사용 가능한 이메일입니다.');
				$("#checkEmailVal").val("Y");
				$('#USER_MAIL').attr("disabled",true);
			} else {
				alert('이미 사용하고 있는 이메일입니다.');
				$("#checkEmailVal").val("N");
			}
		}, error : function (){
			alert("에러가 발생하였습니다.");
		}
	});
}

function checkNickName() {  //닉네임 유효성 검사
    var NickNameValue = $('#USER_NICKNAME').val();
    if (!isValidNickname(NickNameValue)) {
        alert('올바른 닉네임 형식이 아닙니다.');  //공백, 한글 모음, 특수문자 입력시 메시지 출력
        return;
    }

	$.ajax({  //닉네임 중복여부 검사
		type : 'POST',
		url :  '/user/checkNickName',
		data : { USER_NICKNAME : $('#USER_NICKNAME').val()},
		dataType : "text",
		async : false,
		success : function(data) {
			if (data == 'true') { 
				alert('사용 가능한 닉네임입니다.');
				$("#checkNickNameVal").val("Y");
				$('#USER_NICKNAME').attr("disabled",true);
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
    var PasswordValue = $('#USER_PASSWORD').val();
    var NameValue = $('#USER_NAME').val();
    var NickNameValue = $('#USER_NICKNAME').val();
    var EmailValue = $('#USER_MAIL').val();
    var GenreValue1 = $('#USER_PICK1').val();
    var GenreValue2 = $('#USER_PICK2').val();
    var GenreValue3 = $('#USER_PICK3').val();
    var MypageValue = $('input[name="USER_MYPAGE"]:checked').val();

    if (EmailValue.trim() === '') {
        alert('이메일을 입력하세요.');  //이메일을 입력하지 않았을 경우 메시지 출력
        return;
    }

    if($("#checkEmailVal").val() == 'N') {
        alert('이메일 중복확인을 해야 합니다.');  //이메일 중복확인을 누르지 않았을 경우 메시지 출력
        return;
    }

    if (PasswordValue.trim() === '') {
        alert('패스워드를 입력하세요.');  //패스워드를 입력하지 않았을 경우 메시지 출력
        return;
    }

    if($("#USER_PASSWORD").val() != $("#USER_PASSWORD2").val()) {
        alert('패스워드가 일치하지 않습니다.');  //패스워드1과 패스워드2가 일치하지 않았을 경우 메시지 출력
        return;
    }

    if (NameValue.trim() === '') {
        alert('이름을 입력하세요.');  //이름을 입력하지 않았을 않았을 경우 메시지 출력
        return;
    }

    if (NickNameValue.trim() === '') {
        alert('닉네임을 입력하세요.');  //닉네임을 입력하지 않았을 경우 메시지 출력
        return;
    }

    if($("#checkNickNameVal").val() == 'N') {
        alert('닉네임 중복확인을 해야 합니다.');  //닉네임 중복확인을 누르지 않았을 경우 메시지 출력
        return;
    }

    if (GenreValue1 === '선택안함' || GenreValue2 === '선택안함' ||GenreValue3 === '선택안함') {
        alert('선호 장르는 3개 모두 선택해야 합니다.'); // 장르 선택 3개를 모두 하지 않았을 경우 메시지 출력
        return;
    }

    if (!MypageValue) {
        alert('마이 페이지 공개 여부를 선택해야 합니다.'); // 마이페이지 공개 여부를 선택하지 않았을 경우 메시지 출력
        return;
    }
	
	$('#USER_ID').attr("disabled",false);  //아이디,이메일,닉네임 중복검사 받으면 수정불가
	$('#USER_MAIL').attr("disabled",false);
	$('#USER_NICKNAME').attr("disabled",false);
	
	frmSubmit.submit();
	return false;
}

function isValidEmail(email) {  //이메일 유효성 검사 함수
    var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
}

function isValidNickname(nickname) {  //닉네임 유효성 검사 함수
    var NicknameRegex = /^[a-zA-z0-9ㄱ-ㅎ가-힣]+$/;
    return NicknameRegex.test(nickname)
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
    <td><input type="text" name="USER_ID" id="USER_ID" disabled="true" value="${user.USER_ID }"></td>
</tr>
<tr>
    <td align=right>이메일&nbsp;</td>
    <td><input type="e-mail" name="USER_MAIL" id="USER_MAIL" value="${user.USER_MAIL }">
       <input type="button" value="중복확인" onclick="javascript:checkEmail();"></td>
</tr>
<tr>
    <td align=right>패스워드&nbsp;</td> 
    <td><input type="password" name="USER_PASSWORD" id="USER_PASSWORD"></td>
</tr>
<tr> 
    <td align=right>패스워드(확인)&nbsp;</td>
    <td><input type="password" name="USER_PASSWORD2" id="USER_PASSWORD2"></td>
</tr>
<tr> 
    <td align=right>이름&nbsp;</td>
    <td><input type="text" name="USER_NAME" id="USER_NAME" required value="${user.USER_NAME }"></td>
</tr>
<tr>
    <td align=right>닉네임&nbsp;</td>
    <td><input type="text" name="USER_NICKNAME" id="USER_NICKNAME" value="${user.USER_NICKNAME }">
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
    <td><input type="radio" name="USER_MYPAGE" value="Y" <c:if test="${user.USER_MYPAGE eq 'Y'}">checked</c:if>>공개
           <input type="radio" name="USER_MYPAGE" value="N" <c:if test="${user.USER_MYPAGE eq 'N'}">checked</c:if>>비공개
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