package com.myshop.exception;

public class OutOfStockException extends RuntimeException {
	
	//상품 주문 수량보다 재고의 수가 적을때 발생시키는 exception
	public OutOfStockException(String message) {
		super(message);
	}
	
}
