package com.springproject.dto;

import lombok.Getter;
import lombok.Setter;

//해당 클래스의 필드의 getter, setter 메소드를 생성해준다.
@Getter
@Setter
public class Dept {
	private String deptno; //부서번호
	private String dept; //부서이름
	private String loc; //지역
}
