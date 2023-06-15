package com.springquiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
	private String studentno; //학생번호
	private String name; //학생이름
	private int age; //학생나이
	private String java; //자바점수
	private String orcle; //오라클 점수
}
