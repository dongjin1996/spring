package com.fastfood.dto;

import org.modelmapper.ModelMapper;

import com.fastfood.entity.ItemImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemImgDto {
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//entity를 DTO로 변환
	public static ItemImgDto of(ItemImg itemImg) {
		return modelMapper.map(itemImg, ItemImgDto.class);
	}
}
