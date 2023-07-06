package com.fastfood.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.fastfood.entity.ItemImg;
import com.fastfood.repository.ItemImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
	
	private String itemImgLocation = "c:/fastfood/menu";
	private final ItemImgRepository itemImgRepository;
	private final FileService fileService;
	
	
	/*
	 * 1.이미지 저장
	 * 2.item_img 테이블에 저장
	 * */
	
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
		String oriImgName = itemImgFile.getOriginalFilename(); //파일이름 -> 이미지1.jpg
		String imgName = "";
		String imgUrl = "";
		
		//1.파일을 itemImgLocation에 저장
		if(!StringUtils.isEmpty(oriImgName)) {
			//oriImgName이 빈문자열이 아니라면 이미지 파일 업로드
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			imgUrl = "/images/item" + imgName;
		}
		
		//2.item_img 테이블에 저장
		itemImg.updateItemImg(oriImgName, imgName, imgUrl);
		itemImgRepository.save(itemImg); 
	}
}
