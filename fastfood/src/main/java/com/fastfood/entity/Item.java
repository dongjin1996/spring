package com.fastfood.entity;

import com.fastfood.constant.ItemSellStatus;
import com.fastfood.dto.ItemFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
	
	@Lob
	@Column(nullable = false, columnDefinition = "longtext")  //상세설명
	private String itemDetail;
	
	@Enumerated(EnumType.STRING)
	private ItemSellStatus itemSellStatus;   //SELL , SOLD_OUT
	
	//item 엔티티 수정
	public void updateItem(ItemFormDto itemFormDto) {
		this.itemNm = itemFormDto.getItemNm();
		this.price = itemFormDto.getPrice();
		this.itemDetail = itemFormDto.getItemDetail();
		this.itemSellStatus = itemFormDto.getItemSellStatus();
	}
}
