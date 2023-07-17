package com.fastfood.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fastfood.dto.OrderDto;
import com.fastfood.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;
	
	@PostMapping(value= "/order")
	public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto,
			BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
			for (FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage()); //에러메시지를 합친다.
			}
			
			return new ResponseEntity<String> (sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
		String email = principal.getName(); //id에 해당하는 정보를 가지고 온다.
		Long orderId;
		
		try {
			orderId = orderService.order(orderDto, email); //주문하기
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Long>(orderId, HttpStatus.OK); //성공시
		
	}
}
