package com.shopmax.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopmax.entity.Member;
import com.shopmax.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional //쿼리문 수행시 에러가 발생하면 변경된 데이터를 이전상태로 콜백시켜줌
@RequiredArgsConstructor //@Autowired를 사용하지 않고 의존성 주입을 시켜준다.
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	
	//회원가입 데이터를 DB에 저장한다.
	public Member saveMember(Member member) {
		validateDuplicateMember(member); //이메일 중복체크
		Member savedMember = memberRepository.save(member);  //insert
		return savedMember; //회원가입된 데이터를 리턴해준다.
	}
	
	//이메일 중복체크
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByEmail(member.getEmail());
		
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	} 
	
	
}
