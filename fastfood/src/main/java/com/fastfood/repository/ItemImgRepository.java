package com.fastfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastfood.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long>{
	
	//select * from item_img where item_id=? order by item_id ASC;
	List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
}
