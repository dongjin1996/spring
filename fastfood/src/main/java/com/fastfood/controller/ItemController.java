package com.fastfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fastfood.dto.ItemFormDto;

@Controller
public class ItemController {
	
	//메뉴 페이지
	@GetMapping (value = "item/menu")
	public String itemMenu() {
		return "/item/itemMenu";
	}
	
	//상품등록 페이지
	@GetMapping(value = "/admin/item/itemForm")
	public String itemForm(Model model) {
		model.addAttribute("itemFormDto", new ItemFormDto());
		return "/item/itemForm";
	}
	
	//상품, 상품이미지 등록(insert)
	public String itemNew() {
		
	}
	
	//상품관리 페이지
	@GetMapping(value = "/admin/item/itemMng")
	public String itemMng() {
		return "/item/itemMng";
	}
	
	//상품수정 페이지
	@GetMapping(value = "/admin/item/itemModify")
	public String itemModify() {
		return "/item/itemModify";
	}
	
	//상품정보*관리자 페이지
	@GetMapping(value = "/admin/item/itemInfo")
	public String itemInfo() {
		return "/item/itemInfo";
	}

}
