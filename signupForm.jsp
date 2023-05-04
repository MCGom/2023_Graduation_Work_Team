<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<form action="signup.jsp" method="post">
<div style="text-align : center;">
<img src="./logo.jpg" width="300px", height="80px" >
</div>
<table align=center>
<tr><td colspan=2 align=center height=40><b>회원가입</b></td></tr>
<tr>
    <td align=right>아이디&nbsp;</td>
    <td><input type="text" name="id">
       <input type="submit" value="중복확인">
    </td>
    </tr>
<tr>
    <td align=right>패스워드&nbsp;</td>
    <td><input type="password" name="ps"></td>
</tr>
<tr> 
    <td align=right>패스워드(확인)&nbsp;</td>
    <td><input type="password" name="ps2"></td>
</tr>
<tr>
    <td align=right>닉네임&nbsp;</td>
    <td><input type="text" name="nickname">
       <input type="submit" value="중복확인">
    </td>
    </tr>
<tr>
<tr>
    <td align=right>이름&nbsp;</td>
    <td><input type="text" name="name" required>
</td> 
</tr>
<tr>
    <td align=right>성별&nbsp;</td>
    <td><select>
	<option value="">성별 선택</option>
	<option value="남">남</option>
	<option value="여">여</option>
	</select
</td> 
</tr>
    
<tr>
    <td align=right>생년월일&nbsp;</td>
    <td><input type="text" name="yyyy" placeholder="년도" size="5" maxlength="4">
	<select>
		<option value="">월</option>
		<option value="">1월</option>
		<option value="">2월</option>
		<option value="">3월</option>
		<option value="">4월</option>
		<option value="">5월</option>
		<option value="">6월</option>
		<option value="">7월</option>
		<option value="">8월</option>
		<option value="">9월</option>
		<option value="">10월</option>
		<option value="">11월</option>
		<option value="">12월</option>
	</select>
          <input type="text" name="dd" placeholder="일" size="5" maxlength="2">
</td>
</tr>
<tr>
    <td align=right>선호장르&nbsp;</td>
    <td><select>
		<option value="">로맨스</option>
		<option value="">판타지</option>
		<option value="">액션</option>
		<option value="">일상</option>
		<option value="">스릴러</option>
          </select>
          <select>
		<option value="">로맨스</option>
		<option value="">판타지</option>
		<option value="">액션</option>
		<option value="">일상</option>
		<option value="">스릴러</option>
          </select>
          <select>
		<option value="">로맨스</option>
		<option value="">판타지</option>
		<option value="">액션</option>
		<option value="">일상</option>
		<option value="">스릴러</option>
          </select>
          
</tr>
<tr>
    <td align=right>마이페이지 공개여부&nbsp;</td>
    <td><input type="radio" name="ok" value="ok">공개
           <input type="radio" name="no" value="no">비공개
</tr>
<tr>
    <td colspan=2 align=center height=50>
        <input type="submit" value="회원가입하기">
    </td>
</tr>
</table>
</form>
</body>
</html>