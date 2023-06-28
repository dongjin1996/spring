package com.myshop.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.myshop.entity.ItemImg;
import com.myshop.repository.ItemImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
	
	@Value("${itemImgLocation}")
	private String itemImgLocation; //C:/shop/item
	
	private final ItemImgRepository itemImgRepository;
	
	private final FileService fileService;
	
	//이미지 저장 메소드
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
		String oriImgName = itemImgFile.getOriginalFilename(); //파일 이름
		String imgName = "";
		String imgUrl = "";
		
		//파일 업로드
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			imgUrl = "/images/item/" + imgName;
		}
		
		//상품 이미지 정보 저장
		itemImg.updateItemImg(oriImgName, imgName, imgUrl);
		itemImgRepository.save(itemImg);
		
	}
	
	//이미지 업데이트 메소드
	public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
		if(!itemImgFile.isEmpty()) { //파일이 있으면
			ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
					.orElseThrow(EntityNotFoundException::new);
			
			//기존 이미지 파일 삭제
			if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
				// C:/shop/item/a110f979-1467-4c7e-8346-52373e55018d.jpg
				fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
			}
			
			//수정된 이미지 파일 업로드
			String oriImgName = itemImgFile.getOriginalFilename(); 
			String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			String imgUrl = "/images/item/" + imgName;
			
			//★ savedItemImg는 현재 영속상태이므로 데이터를 변경하는 것만으로 변경감지 기능이 동작하여 트랜잭션이 끝날때 update쿼리가 실행된다.
			//-> 엔티티가 반드시 영속상태여야 한다.
			savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
		}
	}
}
