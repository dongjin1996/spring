package com.fastfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastfood.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	
	List<OrderItem> findByItemIdOrderByIdAsc(Long itemId);
}
