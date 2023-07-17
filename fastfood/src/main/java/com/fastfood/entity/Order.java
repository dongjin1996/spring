package com.fastfood.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fastfood.constant.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@ToString
@Table(name = "orders")
@Entity
public class Order {
	
	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;   //주문고유아이디
	
	
	private LocalDateTime orderDate;  //주문날짜
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;  //주문상태
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;  
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
			orphanRemoval = true, fetch = FetchType.LAZY)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	public void addOrderItem(OrderItem orderItem) {
		this.orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
	
	//order 객체를 생성
	public static Order createOrder(Member member, List<OrderItem> orderItemList) {
		Order order = new Order();
		order.setMember(member);
		
		for(OrderItem orderItem : orderItemList) {
			order.addOrderItem(orderItem);
		}
		
		order.setOrderStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		
		return order;
	}
	
	//총 주문 금액
	public int getTotalPrice() {
		int totalPrice = 0;
		for(OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
	}
	
	//주문취소
	public void cancelOrder() {
		this.orderStatus = OrderStatus.CANCEL;
		
		//재고를 원래대로 돌려 놓는다.
		for(OrderItem orderItem: orderItems) {
			orderItem.cancel();
		}
			
		}
	}
	

