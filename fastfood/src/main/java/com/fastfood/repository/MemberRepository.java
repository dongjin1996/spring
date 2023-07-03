package com.fastfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastfood.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Member findByEmail(String email);
}
