package com.fastfood.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fastfood.dto.ItemFormDto;
import com.fastfood.service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {
	
	private final ItemService itemService;
	
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
	@PostMapping(value = "/admin/item/itemForm")
	public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
			Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
		
		if(bindingResult.hasErrors()) {
			return "item/itemForm";
		}
		
		//상품 등록전에 첫번째 이미지가 있는지 없는지 검사(첫번째 이미지는 필수 입력값)
		if(itemImgFileList.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입니다.");
			return "item/itemForm";
		}
		
		try {
			itemService.saveItem(itemFormDto, itemImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품등록 중 에러가 발생했습니다.");
			return "item/itemForm";
		}
		
		return "redirect:/";
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
