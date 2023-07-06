package com.shopmax.entity;



import com.shopmax.constant.ItemSellStatus;
import com.shopmax.dto.ItemFormDto;

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

@Entity  //엔티티 클래스로 정의
@Table(name="item") //테이블 이름 지정
@Getter
@Setter
@ToString
public class Item extends BaseEntity{
	
	@Id
	@Column(name = "item_id") // 테이블로 생성될때 컬럼이름을 지정해준다.
	@GeneratedValue(strategy = GenerationType.AUTO) //기본키를 자동으로 생성해주는 전략 사용
	private Long id; //상품코드
	
	@Column(nullable = false, length = 50) //not null여부, 컬럼 크기 지정
	private String itemNm; //상품명
	
	@Column(nullable = false)
	private int price; //가격
	
	@Column(nullable = false)
	private int stockNumber; //재고수량 - > stock_number
	
	@Lob //clob과 같은 큰타입의 문자타입으로 컬럼을 만든다.
	@Column(nullable = false, columnDefinition = "longtext")
	private String itemDetail; //상품상세설명 -> item_detail
	
	@Enumerated(EnumType.STRING) //enum의 이름을 DB의 저장
	private ItemSellStatus itemSellStatus; //판매상태 (SELL, SOLD_OUT) -> item_sell_status
	
	
	//item 엔티티 수정
	public void updateItem(ItemFormDto itemFormDto) {
		this.itemNm = itemFormDto.getItemNm();
		this.price = itemFormDto.getPrice();
		this.stockNumber = itemFormDto.getStockNumber();
		this.itemDetail = itemFormDto.getItemDetail();
		this.itemSellStatus = itemFormDto.getItemSellStatus();
	}
	
}
