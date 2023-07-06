package com.fastfood.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fastfood.constant.ItemSellStatus;
import com.fastfood.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	
	List<Item> findByItemNm(String itemNm);
	
	List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus itemSellStatus);
	
	List<Item> findByPriceBetween(Integer price1, Integer price2);
	
	List<Item> findByRegTimeAfter(LocalDateTime regTime);
	
	List<Item> findByItemSellStatusNotNull();
	
	List<Item> findByItemDetailLike(String itemDetail);
	
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
	
	List<Item> findByPriceLessThanOrderByPriceDesc(int price);
	
	@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
	List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
	
	@Query(value = "select * from where item_detail like %:itemDetail% order by price desc", nativeQuery = true)
	List<Item> findByItemDetailByNative(@Param("itemDetail")String itemDetail);
	
	@Query("select i from Itm i where i.price >= :price")
	List<Item> findByPrice(@Param("price") int price);
	
	@Query("select i from Item i where i.itemNm = :itemNm and i.itemSellStatus = :sell")
	List<Item> findByitemNmByitemSellStatus(@Param("itemNm")String itemNm, @Param("sell")ItemSellStatus sell);
}
