package com.fastfood.dto;

import java.util.ArrayList;
import java.util.List;

import com.fastfood.entity.Cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartListDto {
	
	private Long cartId; //카트아이디
	
	private List<CartItemDto> cartItemDtoList = new ArrayList<>();
	
	public CartListDto(Cart cart) {
		this.cartId = cart.getId();
	}
	
	public void addCartItemDto(CartItemDto cartItemDto) {
		this.cartItemDtoList.add(cartItemDto);
	}
}
