package com.fastfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fastfood.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Member findByEmail(String email);
	
	@Modifying
	@Query(value= "update member m set m.password = :password where m.id = :id", nativeQuery = true)
	void updateUserPassword (@Param("id")Long id, @Param("password")String password);
}
