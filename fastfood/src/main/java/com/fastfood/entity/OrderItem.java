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
@Table(name = "order_item")
@Entity
public class OrderItem extends BaseEntity{
	
	@Id
	@Column(name = "order_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int orderPrice; //주문가격
	
	private int count;  //주문수량
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	//주문할 상품하고 주문 수량을 통해 orderItem객체를 만듬
	public static OrderItem createOrderItem(Item item, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setCount(count);
		orderItem.setOrderPrice(item.getPrice());
		
		item.removeStock(count); //재고감소
		
		return orderItem;
	}
	
	public int getTotalPrice() {
		return orderPrice * count;
	}
	
	//재고를 원래대로
	public void cancel() {
		this.getItem().addStock(count);
	}
}
