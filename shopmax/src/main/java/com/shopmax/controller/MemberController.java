package com.shopmax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
	
	//문의하기
	@GetMapping (value = "members/qa")
	public String qa() {
		return "member/qa";
	}
	
	//로그인 화면 띄우기
	
	@GetMapping (value = "/members/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}
	
	@GetMapping (value = "/members/new")
	//회원가입 화면 띄우기
	public String memberForm() {
		return "member/memberForm";
	}
}
