package com.fastfood.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fastfood.dto.CartDto;
import com.fastfood.dto.CartListDto;
import com.fastfood.service.Cartservice;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
	private final Cartservice cartservice;
	
	@PostMapping(value = "/cart")
	public @ResponseBody ResponseEntity cart(@RequestBody @Valid CartDto cartDto,
			BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
			for (FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage()); //에러메시지 합친다.
			}
			
			return new ResponseEntity<String> (sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
		String email = principal.getName();
		Long cartId;
		
		try {
			cartId = cartservice.cart(cartDto, email);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Long>(cartId, HttpStatus.OK);
	}
	
	
	@GetMapping (value = "/cart/cartlist")
	public String cartList() {
		return "cart/cartList";
	}
	
	
	//카트리스트
	@GetMapping(value= {"/carts", "/carts/{page}"})
	public String CartList(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
		
		//1.한페이지 당 4개의 데이터를 가지고 오도록 설정
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 9);
		
		//2.서비스 호출
		Page<CartListDto> cartListDtoList = cartservice.getCartList(principal.getName(), pageable);
		
		//3. 서비스에서 가져온 값들을 view단에 model을 이용해 전송
		model.addAttribute("carts", cartListDtoList);
		model.addAttribute("maxPage", 5); 
		
		return "cart/cartList";
	}
	
	@DeleteMapping("/cartItem/{cartItemId}")
	public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal) {
		
		//1.본인pub인증
		if(!cartservice.validateCart(cartItemId, principal.getName())) {
			return new ResponseEntity<String> ("카트 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		
		//2.카트삭제
		cartservice.deleteCartItem(cartItemId);
		
		return new ResponseEntity<Long> (cartItemId, HttpStatus.OK);
	}
}
