package com.toonplus.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/home")  //홈 화면 띄우기
	public String signup() {
		
		return "home.html";
	}

	@GetMapping("/")  //아무것도 입력 안했을 경우 홈으로 전송
	public String root() {

		return "home.html";
	}
}
