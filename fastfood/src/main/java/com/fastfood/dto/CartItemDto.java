package com.fastfood.dto;

import com.fastfood.entity.CartItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
	
	public CartItemDto(CartItem cartItem, String imgUrl) {
		this.itemNm = cartItem.getItem().getItemNm();
		this.count = cartItem.getCount();
		this.cartPrice = cartItem.getCartPrice();
		this.imgUrl = imgUrl;
		this.id = cartItem.getId();
	}
	
	private Long id;
	
	private String itemNm; 
	
	private int count;
	
	private int cartPrice; //주문금액
	
	private String imgUrl; //상품 이미지 경로
}
