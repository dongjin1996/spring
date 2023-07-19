package com.fastfood.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fastfood.constant.ItemSellStatus;
import com.fastfood.dto.ItemRankDto;
import com.fastfood.entity.Item;


public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom{
	
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
	
	@Query("select i from Item i where i.price >= :price")
	List<Item> findByPrice(@Param("price") int price);
	
	@Query("select i from Item i where i.itemNm = :itemNm and i.itemSellStatus = :sell")
	List<Item> findByitemNmByitemSellStatus(@Param("itemNm")String itemNm, @Param("sell")ItemSellStatus sell);
	
	//인기상품 Query  ->DTO로 바로 받고싶으면 interface에서 추상메소드로 작성  -> NativeQuery쓸때 별칭써줘야 인식가능
	@Query(value = "select data.num num, item.item_id id, item.item_nm itemNm, item.price price, item_img.img_url imgUrl, item_img.repimg_yn repimgYn \n"
			+ "            from item \n"
			+ "			inner join item_img on (item.item_id = item_img.item_id)\n"
			+ "			inner join (select @ROWNUM\\:=@ROWNUM+1 num, groupdata.* from\n"
			+ "			            (select order_item.item_id, count(*) cnt\n"
			+ "			              from order_item\n"
			+ "			              inner join orders on (order_item.order_id = orders.order_id)\n"
			+ "			              where orders.order_status = 'ORDER'\n"
			+ "			             group by order_item.item_id order by cnt desc) groupdata,\n"
			+ "                          (SELECT @ROWNUM\\:=0) R \n"
			+ "                          limit 6) data\n"
			+ "			on (item.item_id = data.item_id)\n"
			+ "			where item_img.repimg_yn = 'Y'\n"
			+ "			order by num;", nativeQuery = true)
	List<ItemRankDto> getItemRankList();
}
