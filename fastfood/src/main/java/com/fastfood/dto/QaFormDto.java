package com.fastfood.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;

import com.fastfood.entity.Qa;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QaFormDto {
	private Long id;
	
	@NotBlank(message = "문의제목을 입력해주세요.")
	private String qaNm;
	
	@NotBlank(message = "문의 내용을 입력해주세요.")
	private String qaContent;
	
	private LocalDateTime qaDate;
	
	private LocalDateTime regTime;  //등록일자
	
	private String createBy;  //등록자
	
	private static ModelMapper modelMapper = new ModelMapper();
	

	
	//dto -> entity로 바꿈
	public Qa createQa() {
		return modelMapper.map(this, Qa.class);
	}
	
	public static QaFormDto of(Qa qa) {
		return modelMapper.map(qa, QaFormDto.class);
	}
}
