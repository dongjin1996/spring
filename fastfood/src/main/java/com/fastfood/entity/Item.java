package com.fastfood.entity;

import com.fastfood.constant.ItemSellStatus;
import com.fastfood.dto.ItemFormDto;
import com.fastfood.exception.OutOfStockException;

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
	
	@Column(nullable = false)
	private int stockNumber; //재고수량
	
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
	
	//재고를 감소시킨다.
	public void removeStock(int stockNumber) {
		int restStock = this.stockNumber - stockNumber; //남은 재고 수량
		
		if(restStock < 0 ) {
			throw new OutOfStockException("상품의 재고가 부족합니다." + "현재 재고수량:" + this.stockNumber);
		}
		
		this.stockNumber = restStock; //남은 재고 수량 반영
	}
	
	//재고 증가
	public void addStock(int stockNumber) {
		this.stockNumber += stockNumber;
	}
}
