package com.fastfood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "cart_item")
@Entity
public class CartItem extends BaseEntity{
	
	@Id
	@Column(name = "cart_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int count;  //수량
	
	private int cartPrice; //장바구니가격
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	//주문할 상품과 주문수량을 통해 CartItem객체를 만듬
	public static CartItem createCartItem(Item item, int count) {
		CartItem cartItem = new CartItem();
		cartItem.setItem(item);
		cartItem.setCount(count);
		cartItem.setCartPrice(item.getPrice());
		
		return cartItem;
	}
}
