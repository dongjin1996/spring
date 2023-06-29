package com.fastfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
	
	//어바웃 페이지
	@GetMapping(value = "member/about")
	public String about() {
		return "member/about";
	}
	
	//문의하기 페이지
	@GetMapping(value = "member/qa")
	public String qa() {
		return "member/qa";
	}
	
	//문의리스트 페이지
	@GetMapping(value = "/member/qaList")
	public String qaList() {
		return "member/qaList";
	}
	
	//문의보기 페이지
	@GetMapping(value = "/member/qaInfo")
	public String qaInfo() {
		return "member/qaInfo";
	}
	
	//로그인 페이지
	@GetMapping(value = "/member/login")
	public String loginMember() {
		return "member/memberLoginForm";
	} 
	
	//회원가입 페이지
	@GetMapping(value = "/member/new")
	public String memberForm() {
		return "member/memberForm";
	}
	
	//비밀번호 찾기 페이지
	@GetMapping(value = "/member/pwdfind")
	public String findPwd() {
		return "member/memberFind";
	}
}
