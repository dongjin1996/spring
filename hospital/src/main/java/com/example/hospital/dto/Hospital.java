package com.example.hospital.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hospital {
	
	private int num; //글번호
	private String name; //이름
	private String pwd; //비밀번호
	private String birth; //생일
	private String email; //이메일
	private String symptom;  //증상
	private String tel;  //전화번호
	private String opinion; //소견
	private String created; //등록일
}
