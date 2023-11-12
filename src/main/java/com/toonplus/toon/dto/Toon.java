package com.toonplus.toon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//20230920추가
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Toon {
	private String TOON_IMAGELINK;
	private String TOON_LINK;
	private String TOON_PLATFORM;
	private String TOON_NAME;
	private int TOON_LIKES;
}

