package com.toonplus.email.dto;

import lombok.Data;

@Data
public class EmailVO {
	private String email;
    private String title;
    private String content;
    
	public EmailVO(String email, String title, String content) {
		super();
		this.email = email;
		this.title = title;
		this.content = content;
	}
    
    
}
