package com.toonplus.email.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.toonplus.email.dto.EmailVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceImpl {
	
	private JavaMailSender emailSender;
	 
    public void sendSimpleMessage(EmailVO vo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(vo.getEmail());
        message.setSubject(vo.getTitle());
        message.setText(vo.getContent());
        emailSender.send(message);
    }
	
}
