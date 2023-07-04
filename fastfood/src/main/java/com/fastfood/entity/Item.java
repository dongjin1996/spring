package com.fastfood.entity;

import com.fastfood.constant.ItemSellStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "item")
public class Item extends BaseEntity{
	
	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;   //상품고유 아이디
	
	@Column(nullable = false)  //상품이름
	private String itemNm;
	
	@Column(nullable = false)  //가격
	private int price;
	
	@Column(nullable = false)  //상세설명
	private String itemDetail;
	
	@Enumerated(EnumType.STRING)
	private ItemSellStatus itemSellStatus;   //SELL , SOLD_OUT
}
