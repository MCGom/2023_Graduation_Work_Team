package com.toonplus.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private String USER_ID;
	private String USER_MAIL;
	private String USER_PASSWORD;
	private String USER_NICKNAME;
	private String USER_NAME;
	private String USER_GENDER;
	private String USER_PICK1;
	private String USER_PICK2;
	private String USER_PICK3;
	private String USER_MYPAGE;
	private String USER_FAVORITE;
	private String USER_INTRODUCE;
	private String GENRE_ACTION;
	private String GENRE_ROMANCE;
	private String GENRE_DRAMA;
	private String GENRE_FANTASY;
	private String GENRE_COMEDY;
	private String GENRE_THRILLER;
	private String GENRE_ACADEMY;
	private String USER_LIKE;
	@Override
	public String toString() {
		return "User [USER_ID=" + USER_ID + ", USER_MAIL=" + USER_MAIL
				+ ", USER_PASSWORD=" + USER_PASSWORD + ", USER_NICKNAME=" + USER_NICKNAME + ", USER_NAME="
				+ USER_NAME + ", USER_GENDER=" + USER_GENDER + ", USER_PICK1=" + USER_PICK1 + ", USER_PICK2=" + USER_PICK2
				+ ", USER_PICK3=" + USER_PICK3 + ", USER_MYPAGE=" + USER_MYPAGE + "]";
	}


}
