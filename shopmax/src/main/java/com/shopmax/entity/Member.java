package com.shopmax.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.shopmax.constant.Role;
import com.shopmax.dto.MemberFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {
	
	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //회원코드
	
	@Column(nullable = false, unique = true, length = 255) // unique= 중복된 값이 들어올 수 없다.
	private String email;  //유저이메일
	
	@Column(nullable = false, length = 255)
	private String name;  //유저이름
	
	@Column(nullable = false, length = 255)
	private String password;  // 패스워드
	
	@Column(nullable = false, length = 255)
	private String address;  //주소
	
	@Enumerated(EnumType.STRING)
	private Role role;  //역할  (ADMIN, USER)
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		//패스워드 암호화
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		
		//MemberFormDto를 -> Member 엔티티 객체로 변환
		
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setAddress(memberFormDto.getAddress());
		member.setPassword(password);
		//member.setRole(Role.ADMIN);  //관리자로 가입할때
		member.setRole(Role.USER); //일반 사용자로 가입할때
		
		return member;
		
	}
}
