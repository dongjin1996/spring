package com.fastfood.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fastfood.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	@Query("select c from Cart c where c.member.email = :email ")
	List<Cart> findCarts(@Param("email") String email, Pageable pageable);
	
	@Query("select count(c) from Cart c where c.member.email =:email")
	Long countCart(@Param("email") String email);
	
	
}
