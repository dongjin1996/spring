package com.fastfood.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fastfood.dto.OrderHistDto;
import com.fastfood.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
	private final OrderService orderService;
	
	@GetMapping (value = "/cart/cartlist")
	public String cartList() {
		return "cart/cartList";
	}
	
	
	//카트리스트
	@GetMapping(value= {"/carts", "/carts/{page}"})
	public String CartListt(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
		
		//1.한페이지 당 4개의 데이터를 가지고 오도록 설정
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
		
		//2.서비스 호출
		Page<OrderHistDto> orderHistDtoList = 
				orderService.getOrderList(principal.getName(), pageable);
		
		//3. 서비스에서 가져온 값들을 view단에 model을 이용해 전송
		model.addAttribute("orders", orderHistDtoList);
		model.addAttribute("maxPage", 5); 
		
		return "cart/cartList";
	}
}
