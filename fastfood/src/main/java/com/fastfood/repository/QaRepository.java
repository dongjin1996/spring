package com.fastfood.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fastfood.entity.Qa;

public interface QaRepository extends JpaRepository<Qa, Long>, QaRepositoryCustom{
	
	List<Qa> findByQaNm(String qaNm);
	
	List<Qa> findByQaDateAfter(LocalDateTime qaDate);
	
	List<Qa> findByQaContentLike(String qaContent);
	
	List<Qa> findByQaNmOrQaContent(String qaNm, String qaContent);
	
	
	@Query("select i from Qa i where i.qaContent like %:qaContent%")
	List<Qa> findByQaContent(@Param("qaContent") String qaContent);
	
	//@Query("select i from Qa i where i.createBy like %:createBy%")
	//List<Qa> findByCreateBy(@Param("createBy") String createBy);
	
}
