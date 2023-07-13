package com.fastfood.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QaSearchDto {
	private String searchDateType;
	private String searchBy;
	private String searchQuery = "";
}
