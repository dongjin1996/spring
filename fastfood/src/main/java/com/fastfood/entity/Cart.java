package com.fastfood.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cart_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	

	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,
			orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CartItem> cartItems = new ArrayList<>();
	
	public void addCartItem(CartItem cartItem) {
		this.cartItems.add(cartItem);
		cartItem.setCart(this);
	}
	
	//cart 객체를 생성
	public static Cart createCart(Member member, List<CartItem> cartItemList) {
		Cart cart = new Cart();
		cart.setMember(member);
		
		for(CartItem cartItem : cartItemList) {
			cart.addCartItem(cartItem);
		}
		
		return cart;
	}
}
