package com.fastfood.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fastfood.dto.MailDto;
import com.fastfood.dto.MemberFormDto;
import com.fastfood.dto.QaFormDto;
import com.fastfood.dto.QaSearchDto;
import com.fastfood.entity.Member;
import com.fastfood.entity.Qa;
import com.fastfood.service.MemberService;
import com.fastfood.service.QaService;
import com.fastfood.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final QaService qaService;

	private final UserService userService;
	/* private final SendEmailService sendEmailService; */

	// 어바웃 페이지
	@GetMapping(value = "member/about")
	public String about() {
		return "member/about";
	}

	// 문의하기 페이지
	@GetMapping(value = "/user/member/qa")
	public String qaForm(Model model) {
		model.addAttribute("qaFormDto", new QaFormDto());
		return "member/qa";
	}

	// 문의 등록 (insert)
	@PostMapping(value = "/user/member/qa")
	public String qaNew(@Valid QaFormDto qaFormDto, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "member/qa";
		}

		try {
			qaService.saveQa(qaFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "문의등록 중 에러가 발생했습니다.");
			return "member/qa";
		}

		return "redirect:/";
	}

	// 문의보기 페이지
	@GetMapping(value = "/admin/qa/{qaId}")
	public String qaInfo(Model model, @PathVariable("qaId") Long qaId) {
		QaFormDto qaFormDto = qaService.getQaDtl(qaId);
		model.addAttribute("qa", qaFormDto);
		return "member/qaInfo";
	}

	// 문의리스트 페이지
	@GetMapping(value = { "/admin/qaList", "/admin/qaList/{page}" })
	public String qaList(QaSearchDto qaSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {

		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

		Page<Qa> qas = qaService.getAdminQaPage(qaSearchDto, pageable);

		model.addAttribute("qas", qas);
		model.addAttribute("qaSearchDto", qaSearchDto);
		model.addAttribute("maxPage", 5);

		return "member/qaList";
	}

	// 문의 삭제
	@DeleteMapping("/qa/{qaId}/delete")
	public @ResponseBody ResponseEntity deleteQa(@PathVariable("qaId") Long qaId, Principal principal) {

		qaService.deleteQa(qaId);

		return new ResponseEntity<Long>(qaId, HttpStatus.OK);
	}

	// 로그인 페이지
	@GetMapping(value = "/member/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}

	// 회원가입 화면 띄우기
	@GetMapping(value = "/member/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}

	// 회원가입 페이지
	@PostMapping(value = "/member/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		// @Valid: 유효성을 검증하려는 객체 앞에 붙인다.
		// BindingResult : 유효성 검증 후의 결과가 들어있다.

		if (bindingResult.hasErrors()) {
			// 에러가 있다면 회원가입 페이지로 이동
			return "member/memberForm";
		}

		try {
			// MemberFormDto -> Member Entity 비밀번호 암호화
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}

		return "redirect:/";
	}

	// 로그인 실패했을때
	@GetMapping(value = "/member/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "member/memberLoginForm";
	}

	// 비밀번호 찾기 페이지
	@GetMapping(value = "/member/pwdfind")
	public String findPwd() {
		return "member/memberFind";
	}
	
	//회원정보 수정 페이지 보기
	@GetMapping(value = {"/user/member", "/user/member/{memberId}"})
	public String memberDtl(@PathVariable("memberId") Long memberId, Model model, Principal principal) {
		
		try {
			MemberFormDto memberFormDto = memberService.getMemberDtl(memberId);
			model.addAttribute("memberFormDto", memberFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "회원정보를 불러오는중 에러가 발생했습니다.");
			
			model.addAttribute("memberFormDto", new MemberFormDto());
			return "member/memberForm";
		}
		
		return "member/memberModify";
	}
	
	
	//회원정보 수정(update)
	@PostMapping(value = "/user/member/{memberId}")
	public String memberUpdate(@Valid MemberFormDto memberFormDto, Model model, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "member/memberForm";
		}
		
		return "redirect:/";
	}
	
	/*
	 * // 비밀번호찾기 이메일 보내기
	 * 
	 * @GetMapping("/check/findPw") public @ResponseBody Map<String, Boolean>
	 * pw_find(String email, String name) { Map<String, Boolean> json = new
	 * HashMap<>(); boolean pwFindcheck = userService.userEmailCheck(email, name);
	 * 
	 * System.out.println(pwFindcheck); json.put("check", pwFindcheck); return json;
	 * }
	 * 
	 * // 등록된 이메일로 임시비밀번호 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
	 * 
	 * @PostMapping("/check/findPw/sendEmail") public @ResponseBody void
	 * sendEmail(String email, String name) { MailDto dto =
	 * sendEmailService.createMailAndChangePassword(email, name);
	 * sendEmailService.mailSend(dto); }
	 */

}
