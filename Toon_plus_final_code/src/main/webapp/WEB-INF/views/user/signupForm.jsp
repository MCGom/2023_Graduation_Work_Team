<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<meta charset="UTF-8">
<script>
function checkId() {  //아이디 유효성 검사
    var IdValue = $('#USER_ID').val();

    if (!isValidId(IdValue)) {
        alert('올바른 아이디 형식이 아닙니다.');  //공백, 한글 모음, 특수문자 입력시 메시지 출력
        return;
    }

    if (IdValue.length < 2 || IdValue.length > 20) {  //아이디 글자수 제한(없어도 될듯)
        alert('아이디는 2 ~ 20자 사이만 가능합니다.');
        return;
    }

	$.ajax({  //아이디 중복여부 검사
		type : 'POST',
		url :  '/user/checkId',
		data : { USER_ID : $('#USER_ID').val()},
		dataType : "text",
		async : false,
		success : function(data) {
			if (data == 'true') { 
				alert('사용 가능한 아이디입니다.');
				$("#checkIdVal").val("Y");
				$('#USER_ID').attr("disabled",true);
			} else {
				alert('이미 사용하고 있는 아이디입니다.');
				$("#checkIdVal").val("N");
			}
		}, error : function (){
			alert("에러가 발생하였습니다.");
		}
	});
}

function checkEmail() {  //이메일 중복검사
    var emailValue = $('#USER_MAIL').val();

    if (!isValidEmail(emailValue)) {
        alert('올바른 이메일 형식이 아닙니다.');   //일반적인 이메일 형식이 아닐 경우 메시지 출력
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
				alert('사용 가능한 이메일입니다. 이메일 인증번호가 전송되었습니다.');
				$.ajax({  //이메일 중복여부 검사
					type : 'POST',
					url :  '/user/sendEmail',
					data : { USER_MAIL : $('#USER_MAIL').val()},
					dataType : "text",
					async : false,
					success : function(data) {
						alert(data);
					}, error : function (){
						alert("에러가 발생하였습니다.");
					}
				});
						
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
		data : { NICKNAME : $('#USER_NICKNAME').val()},
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
    var IdValue = $('#USER_ID').val();
    var GenderValue = $('#USER_GENDER').val();
    var GenreValue1 = $('#USER_PICK1').val();
    var GenreValue2 = $('#USER_PICK2').val();
    var GenreValue3 = $('#USER_PICK3').val();
    var MypageValue = $('input[name="USER_MYPAGE"]:checked').val();

    if (IdValue.trim() === '') {
        alert('아이디를 입력하세요.');  //아이디를 입력하지 않았을 경우 메시지 출력
        return;
    }

    if($("#checkIdVal").val() == 'N') {
        alert('아이디 중복확인을 해야 합니다.');  //아이디 중복확인을 누르지 않았을 경우 메시지 출력
        return;
    }

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
        alert('패스워드가 일치하지 않습니다.');  //패스워드 1과 패스워드 2가 일치하지 않았을 경우 메시지 출력
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

    if (NameValue.trim() === '') {
        alert('이름을 입력하세요.');  //이름을 입력하지 않았을 경우 메시지 출력
        return;
    }

    if (GenderValue === '') {
        alert('성별을 선택하세요.');  // 성별이 선택되지 않았을 경우 메시지 출력
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
	
	$('#USER_ID').attr("disabled",false);   //아이디,이메일,닉네임 중복검사 받으면 수정불가
	$('#USER_MAIL').attr("disabled",false);
	$('#USER_NICKNAME').attr("disabled",false);
	
	frmSubmit.submit();
	return false;
}

function isValidId(id) {  //아이디 유효성 검사 함수
    var idRegex = /^[a-zA-z0-9ㄱ-ㅎ가-힣]+$/;
    return idRegex.test(id)
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
    <td><input type="text" name="USER_ID" id="USER_ID">
       <input type="button" value="중복확인" onclick="javascript:checkId();">
    </td>
    </tr>
<tr>
    <td align=right>이메일&nbsp;</td>
    <td><input type="e-mail" name="USER_MAIL" id="USER_MAIL">
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
    <td align=right>닉네임&nbsp;</td>
    <td><input type="text" name="USER_NICKNAME" id="USER_NICKNAME">
       <input type="button" value="중복확인" onclick="javascript:checkNickName();">
    </td>
    </tr>
<tr>
<tr>
    <td align=right>이름&nbsp;</td>
    <td><input type="text" name="USER_NAME" id="USER_NAME" required>
</td> 
</tr>
<tr>
    <td align=right>성별&nbsp;</td>
    <td><select name="USER_GENDER" id="USER_GENDER" required>
	<option value="">성별 선택</option>
	<option value="M">남</option>
	<option value="F">여</option>
	</select>
</td> 
</tr>
<tr>
    <td align=right>선호장르&nbsp;</td>
    <td><select name="USER_PICK1" id="USER_PICK1" required>
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
    <td><img src="/resources/images/qm.jpg" width="5px", height="5px" title='공개합니다'>
<input type="radio" name="USER_MYPAGE" value="Y">공개
           <input type="radio" name="USER_MYPAGE" value="N">비공개
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