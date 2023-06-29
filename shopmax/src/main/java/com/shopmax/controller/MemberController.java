package com.shopmax.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopmax.dto.MemberFormDto;
import com.shopmax.entity.Member;
import com.shopmax.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
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
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	
	//회원가입
	@PostMapping (value = "/members/new")
	public String memberForm(MemberFormDto memberFormDto) {
		//MemberFormDto -> Member Entity, 비밀번호 암호화
		Member member = Member.createMember(memberFormDto, passwordEncoder);
		memberService.saveMember(member);
		
		return "redirect:/";
	}
	
	//로그인 실패했을때
	@GetMapping(value = "/members/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "member/memberLoginForm";
	}
	
	
	
	
	
	
}
