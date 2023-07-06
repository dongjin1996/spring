package com.shopmax.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopmax.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long>{
	//select * from item_img where item_id = ? Order by item_id ASC;
	List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
}
