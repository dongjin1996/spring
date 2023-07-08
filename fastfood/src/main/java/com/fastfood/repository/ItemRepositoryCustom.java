package com.fastfood.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fastfood.dto.ItemSearchDto;
import com.fastfood.entity.Item;

public interface ItemRepositoryCustom {
	Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
