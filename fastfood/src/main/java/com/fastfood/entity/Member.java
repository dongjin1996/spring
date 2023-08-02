package com.fastfood.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fastfood.constant.Role;
import com.fastfood.dto.MemberFormDto;

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
@Getter
@Setter
@ToString
@Table (name = "member")
public class Member extends BaseEntity{
	
	@Id
	@Column (name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //회원코드
	
	@Column(nullable = false, unique = true, length = 255)
	private String email; //이메일 유니크
	
	@Column(nullable = false, length = 255)
	private String name;  //이름
	
	@Column(nullable = false, length = 255)
	private String password; //패스워드
	
	@Column(nullable = false, length = 255)
	private String address;  //주소
	
	@Column(nullable = false, length = 255)
	private String birth; //생일
	
	@Enumerated(EnumType.STRING)
	private Role role;  //회원등급 ADMIN, USER;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		
		//패스워드 암호화
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		
		//MemberDto를 -> Member 엔티티 객체로 변환
		
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setAddress(memberFormDto.getAddress());
		member.setBirth(memberFormDto.getBirth());
		member.setPassword(password);
		member.setRole(Role.ADMIN);  //관리자로 가입
	//	member.setRole(Role.USER);  //일반유저로 가입
		
		return member;
	}
	
	//member 엔티티 수정
	public void updateMember(MemberFormDto memberFormDto) {
		this.name = memberFormDto.getName();
		this.password = memberFormDto.getPassword();
		this.address = memberFormDto.getAddress();
		this.birth = memberFormDto.getBirth();
	}
}
