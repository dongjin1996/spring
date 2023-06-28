package com.myshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.myshop.constant.ItemSellStatus;
import com.myshop.dto.ItemRankDto;
import com.myshop.entity.Item;
import com.querydsl.core.BooleanBuilder;

//JpaRepository : 기본적인 CRUD 및 페이징 처리를 위한 메소드가 정의가 되어있다.
//JpaRepository<사용할 엔티티 클래스, 기본키 타입>
public interface ItemRepository extends JpaRepository<Item, Long>, 
       QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {
	//select * from item where item_nm = ?
	List<Item> findByItemNm(String itemNm);
	
	//select * from item where item_nm = ? or item_detail = ?
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
	
	//select * from item where price < ?
	List<Item> findByPriceLessThan(Integer price);
	
	//select * from item where price < ? order by price desc
	List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
	
	//Dto로 바로 넣어주는 native 쿼리를 사용시에 쿼리문에서 별칭을 반드시 줘야 Dto 객체에서 인식한다.
	//가장 판매순위가 높은 6개의 데이터
	@Query(value = "select data.num num, item.item_id id, item.item_nm itemNm, item.price price, item_img.img_url imgUrl, item_img.repimg_yn repimgYn from item \r\n"
			+ "inner join item_img on (item.item_id = item_img.item_id)\r\n"
			+ "inner join (select rownum num, groupdata.* from \r\n"
			+ "             (select order_item.item_id, count(*) cnt\r\n"
			+ "               from order_item\r\n"
			+ "               inner join orders on (order_item.order_id = orders.order_id)\r\n"
			+ "               where orders.order_status = 'ORDER'\r\n"
			+ "               group by order_item.item_id order by cnt desc) groupdata where rownum <= 6) data \r\n"
			+ "on (item.item_id = data.item_id)\r\n"
			+ "where item_img.repimg_yn = 'Y'\r\n"
			+ "order by num", nativeQuery = true)
	List<ItemRankDto> getItemRankList();
	
	/*
	//퀴즈1
	List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus itemSellStatus);
	List<Item> findByPriceBetween(Integer price1, Integer price2);
	List<Item> findByRegTimeAfter(LocalDateTime regTime);
	List<Item> findByItemSellStatusNull();
	List<Item> findByItemDetailLike(String itemDetail);
	*/
	
	//@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
	//List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
	
	@Query("select i from Item i where i.itemDetail like %?1% order by i.price desc")
	List<Item> findByItemDetail(String itemDetail);
	
	
	@Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
	List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
	
	
	//퀴즈
	/*
	@Query("select i from Item i where i.price >= :price")
	List<Item> getPrice(@Param("price") Integer price);
	
	@Query("select i from Item i where i.itemNm = :itemNm and i.itemSellStatus = :sell")
	List<Item> getItemNmAndItemSellStatus(@Param("itemNm") String itemNm, @Param("sell") ItemSellStatus sell);
	
	@Query(value = "select * from item i where i.item_nm = :itemNm and i.item_sell_status = :#{#sell.name()}", nativeQuery = true)
	List<Item> getItemNmAndItemSellStatus(@Param("itemNm") String itemNm, @Param("sell") ItemSellStatus sell);	
	*/
}
