package com.fastfood.dto;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.fastfood.entity.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
	private Long id;
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@NotEmpty(message = "이메일은 필수 입력 사항입니다.")
	@Email(message = "이메일 형식으로 입력 해주세요.")
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자~16자 사이로 입력해주세요.")
	private String password;
	
	@NotBlank(message = "생년월일을 입력해주세요.")
	private String birth;
	
	@NotEmpty(message = "주소는 필수 입력 값입니다.")
	private String address;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static MemberFormDto of (Member member) {
		return modelMapper.map(member, MemberFormDto.class);
	}
}
