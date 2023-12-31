package com.fastfood.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fastfood.dto.ItemRankDto;
import com.fastfood.dto.ItemSearchDto;
import com.fastfood.dto.MainItemDto;
import com.fastfood.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final ItemService itemService;
	
	@GetMapping(value = "/")
	public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
		List<ItemRankDto> itemsRank = itemService.getItemRankList();
		
		model.addAttribute("items", items);
		model.addAttribute("itemsRank", itemsRank);
		model.addAttribute("itemSearchDto", itemSearchDto);
		
		return "main";
	}
}
