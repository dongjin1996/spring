package com.fastfood.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.entity.Member;
import com.fastfood.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	
	private final MemberRepository memberRepository;
	
	public boolean userEmailCheck(String email, String name) {
		Member member = memberRepository.findByEmail(email);
		if(member!=null && member.getName().equals(name)) {
			return true;
		} else {
			return false;
		}
	}
}
