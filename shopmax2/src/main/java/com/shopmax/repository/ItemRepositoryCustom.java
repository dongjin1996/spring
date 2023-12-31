package com.shopmax.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shopmax.dto.ItemSearchDto;
import com.shopmax.entity.Item;

public interface ItemRepositoryCustom {
	Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
