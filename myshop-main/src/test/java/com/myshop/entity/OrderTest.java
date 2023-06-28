package com.myshop.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.constant.ItemSellStatus;
import com.myshop.repository.ItemRepository;
import com.myshop.repository.MemberRepository;
import com.myshop.repository.OrderItemRepository;
import com.myshop.repository.OrderRepository;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class OrderTest {
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	public Item createItemTest() {
		Item item = new Item();
		item.setItemNm("테스트 상품");
		item.setPrice(10000);
		item.setItemDetail("테스트 상품 상세 설명");
		item.setItemSellStatus(ItemSellStatus.SELL);
		item.setStockNumber(100);
		item.setRegTime(LocalDateTime.now());
//		item.setUpdateTime(LocalDateTime.now());
		
		return item;
	}
	
	
	@Test
	@DisplayName("영속성 전이 테스트")
	public void cascadeTest() {
		Order order = new Order();
		
		for(int i=0; i<3; i++) {
			Item item = this.createItemTest(); //3개의 물건 생성
			itemRepository.save(item);
			
			OrderItem orderItem = new OrderItem();
			orderItem.setItem(item);
			orderItem.setCount(10);
			orderItem.setOrderPrice(1000);
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		
		orderRepository.saveAndFlush(order); //order 엔티티를 저장하면서 강제로 flush(데이터 베이스에 반영)을 해준다.
		em.clear(); //영속성 컨텍스트 초기화
		
		Order saveOrder = orderRepository.findById(order.getId())
				.orElseThrow(EntityNotFoundException::new);
		
		assertEquals(3, saveOrder.getOrderItems().size());
	}
	
	
	public Order createOrder() {
		Order order = new Order();
		
		for(int i=0; i<3; i++) {
			Item item = this.createItemTest(); //3개의 물건 생성
			itemRepository.save(item);
			
			OrderItem orderItem = new OrderItem();
			orderItem.setItem(item);
			orderItem.setCount(10);
			orderItem.setOrderPrice(1000);
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		
		Member member = new Member();
		memberRepository.save(member);
		
		order.setMember(member);
		orderRepository.save(order);
		
		return order;
	}
	
	@Test
	@DisplayName("고아객체 제거 테스트")
	public void orphanRemovalTest() {
		Order order = this.createOrder();
		order.getOrderItems().remove(0); //주문 엔티티(부모)에서 주문상품 엔티티(자식)를 삭제 했을때 orderItem엔티티가 삭제가 된다.
		em.flush();
	}
	
	@Test
	@DisplayName("지연 로딩 테스트")
	public void lazyLoadingTest() {
		Order order = this.createOrder();
		Long orderItemId = order.getOrderItems().get(0).getId(); //order_item 테이블의 첫번째 물건의 id를 구한다.
		
		em.flush();
		em.clear();
		
		OrderItem orderItem = orderItemRepository.findById(orderItemId)
				.orElseThrow(EntityNotFoundException::new);
		
	}
}
