package com.fastfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastfood.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	
	List<CartItem> findByItemIdOrderByIdAsc(Long itemId);
}
