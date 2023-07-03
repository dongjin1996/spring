package com.fastfood.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fastfood.dto.MemberFormDto;
import com.fastfood.entity.Member;
import com.fastfood.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
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
	
	//회원가입 화면 띄우기
	@GetMapping(value = "/member/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	
	//회원가입 페이지
	@PostMapping(value = "/member/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		//@Valid: 유효성을 검증하려는 객체 앞에 붙인다.
		//BindingResult : 유효성 검증 후의 결과가 들어있다.
		
		if(bindingResult.hasErrors()) {
			//에러가 있다면 회원가입 페이지로 이동
			return "member/memberForm";
		}
		
		try {
			//MemberFormDto -> Member Entity 비밀번호 암호화
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		
		return "redirect:/";
	}
	
	//로그인 실패했을때
	@GetMapping(value = "/member/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "member/memberLoginForm";
	}
	
	//비밀번호 찾기 페이지
	@GetMapping(value = "/member/pwdfind")
	public String findPwd() {
		return "member/memberFind";
	}
}
