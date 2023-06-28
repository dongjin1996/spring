package com.myshop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.myshop.dto.ItemDto;
import com.myshop.dto.ItemFormDto;
import com.myshop.dto.ItemImgDto;
import com.myshop.dto.ItemRankDto;
import com.myshop.dto.ItemSearchDto;
import com.myshop.dto.MainItemDto;
import com.myshop.entity.Item;
import com.myshop.entity.ItemImg;
import com.myshop.repository.ItemImgRepository;
import com.myshop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
	private final ItemRepository itemRepository;
	private final ItemImgService itemImgService;
	private final ItemImgRepository itemImgRepository;
	
	//상품 등록
	public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
		
		//상품등록
		Item item = itemFormDto.createItem();
		itemRepository.save(item);
		
		//이미지 등록
		for(int i=0; i<itemImgFileList.size(); i++) {
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(item);
			
			//첫번째 이미지 일때 대표 상품 이미지 여부 지정
			if(i == 0) { 
				itemImg.setRepimgYn("Y");
			} else {
				itemImg.setRepimgYn("N");
			}
			
			itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
		}
		
			return item.getId();
	}
	
	//상품 가져오기
	@Transactional(readOnly = true) //트랜잭션 읽기 전용(변경감지 수행하지 않음) -> 성능향상
	public ItemFormDto getItemDtl(Long itemId) {
		//1. item_img테이블의 이미지를 가져온다.
		List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
		List<ItemImgDto> itemImgDtoList = new ArrayList<>();
		
		//엔티티 객체 -> dto객체로 변환
		for(ItemImg itemImg : itemImgList) {
			ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
			itemImgDtoList.add(itemImgDto);
		}
		
		//2. item테이블에 있는 데이터를 가져온다.
		Item item = itemRepository.findById(itemId)
				                  .orElseThrow(EntityNotFoundException::new);
		
		//엔티티 객체 -> dto객체로 변환
		ItemFormDto itemFormDto = ItemFormDto.of(item);
		
		//상품의 이미지 정보를 넣어준다.
		itemFormDto.setItemImgDtoList(itemImgDtoList);
		
		return itemFormDto;
	}
	
	//상품 수정
	public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
		
		Item item = itemRepository.findById(itemFormDto.getId())
				.orElseThrow(EntityNotFoundException::new);
		
		item.updateItem(itemFormDto);
		
		List<Long> itemImgIds = itemFormDto.getItemImgIds(); //상품 이미지 아이디 리스트를 조회
		
		for(int i=0; i<itemImgFileList.size(); i++) {
			itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
		}
		
		return item.getId();
		
	}
	
	//상품 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		return itemRepository.getAdminItemPage(itemSearchDto, pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		return itemRepository.getMainItemPage(itemSearchDto, pageable);
	}
	
	@Transactional(readOnly = true)
	public List<ItemRankDto> getItemRankList() {
		return itemRepository.getItemRankList();
	}

}
