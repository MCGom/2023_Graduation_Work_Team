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
	private int Toon_idx;
	private String TOON_IMAGELINK;
	private String TOON_LINK;
	private String TOON_PLATFORM;
	private String TOON_NAME;
	private int TOON_LIKES;
	private int TOON_NEWUPDATE;
	private int TOON_UPDATE;
	private String Toon_category;
	private String Review_content;
	private String User_id;



	
}

