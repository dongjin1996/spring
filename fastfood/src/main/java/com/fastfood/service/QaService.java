package com.fastfood.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastfood.dto.QaFormDto;
import com.fastfood.dto.QaSearchDto;
import com.fastfood.entity.Qa;
import com.fastfood.repository.QaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QaService {
	
	private final QaRepository qaRepository;
	
	//Qa 테이블에 내용등록
	public Long saveQa(QaFormDto qaFormDto) throws Exception {
		//1.내용등록
		Qa qa = qaFormDto.createQa();
		qaRepository.save(qa); //insert 저장
		
		return qa.getId();
	}
	
	
	//내용을 가져오는 기능을 할 서비스
	@Transactional(readOnly = true)
	public QaFormDto getQaDtl(Long qaId) {
		
		//1.qa테이블에 있는 데이터를 가져온다
		Qa qa = qaRepository.findById(qaId)
				  .orElseThrow(EntityNotFoundException::new);
		
		//2.QA 엔티티 객체 -> dto로 변환
		QaFormDto qaFormDto = QaFormDto.of(qa);
		
		return qaFormDto;
			
	}
	
	public Long updateQa(QaFormDto qaFormDto) throws Exception {
		
		//1. qa엔티티 가져와서 바꾼다.
		Qa qa = qaRepository.findById(qaFormDto.getId())
							.orElseThrow(EntityNotFoundException::new);
		
		qa.updateQa(qaFormDto);
		
		return qa.getId();
	}
	
	@Transactional(readOnly = true)
	public Page<Qa> getAdminQaPage(QaSearchDto qaSearchDto, Pageable pageable) {
		Page<Qa> qaPage = qaRepository.getAdminQaPage(qaSearchDto, pageable);
		return qaPage;
	}
	
	
}
