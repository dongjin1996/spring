package com.fastfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {
	
	//메뉴 페이지
	@GetMapping (value = "item/menu")
	public String itemMenu() {
		return "/item/itemMenu";
	}

}
