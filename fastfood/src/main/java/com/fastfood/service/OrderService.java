package com.fastfood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.entity.Item;
import com.fastfood.entity.Member;
import com.fastfood.entity.Order;
import com.fastfood.entity.OrderItem;
import com.fastfood.repository.ItemImgRepository;
import com.fastfood.repository.ItemRepository;
import com.fastfood.repository.MemberRepository;
import com.fastfood.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	private final ItemImgRepository itemImgRepository;
	
	//주문하기
	public Long order(com.fastfood.dto.OrderDto orderDto, String email) {
		
		//1.주문할 상품을 조회
		Item item = itemRepository.findById(orderDto.getItemId())
								  .orElseThrow(EntityNotFoundException::new);
		
		//2.현재 로그인한 회원의 이메일을 이용해 회원정보를 조회
		Member member = memberRepository.findByEmail(email);
		
		//3.주문할 상품 엔티티와 주문 수량을 이용하여 주문 상품 엔티티를 생성
		List<OrderItem> orderItemList = new ArrayList<>();
		OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
		orderItemList.add(orderItem);
		
		//4. 회원 정보와 주문할 상품 리스트 정보를 이용하여 주문 엔티티를 생성
		Order order = Order.createOrder(member, orderItemList);
		orderRepository.save(order); //insert
		
		return order.getId();
	}
}
