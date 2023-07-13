package com.fastfood.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fastfood.dto.QaSearchDto;
import com.fastfood.entity.Qa;

public interface QaRepositoryCustom {
	Page<Qa> getAdminQaPage(QaSearchDto qaSearchDto, Pageable pageable);
}
