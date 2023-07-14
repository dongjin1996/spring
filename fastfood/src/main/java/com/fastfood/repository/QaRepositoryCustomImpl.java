package com.fastfood.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;


import com.fastfood.dto.QaSearchDto;
import com.fastfood.entity.QQa;
import com.fastfood.entity.Qa;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class QaRepositoryCustomImpl implements QaRepositoryCustom{
	
	private JPAQueryFactory queryFactory;
	
	public QaRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	//현재 날짜로부터 이전날짜를 구해주는 메소드
	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now(); //현재날짜, 시간
		
		if(StringUtils.equals("all", searchDateType) || searchDateType == null) return null;
		else if(StringUtils.equals("1d", searchDateType))
				dateTime = dateTime.minusDays(1); //현재 날짜로부터 1일전
		else if(StringUtils.equals("1w", searchDateType))
			dateTime = dateTime.minusWeeks(1); //현재 날짜로부터 1주일전
		else if(StringUtils.equals("1m", searchDateType))
			dateTime = dateTime.minusMonths(1); //현재 날짜로부터 1달전
		else if(StringUtils.equals("6m", searchDateType))
			dateTime = dateTime.minusMonths(6); //현재 날짜로부터 6달전
		
		return QQa.qa.regTime.after(dateTime); //Q객체 리턴
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("qaNm", searchBy)) {
			//searchBy가 qaNm이라면 -> 등록자로 검색시
			return QQa.qa.qaNm.like("%"+searchQuery + "%");
		} else if (StringUtils.equals("createBy", searchBy)) {
			return QQa.qa.createBy.like("%" + searchQuery + "%");
		}
		return null; //쿼리문을 실행하지 않는다
	}
	

	@Override
	public Page<Qa> getAdminQaPage(QaSearchDto qaSearchDto, Pageable pageable) {
		
		List<Qa> content = queryFactory
						.selectFrom(QQa.qa)
						.where(regDtsAfter(qaSearchDto.getSearchDateType()),
								searchByLike(qaSearchDto.getSearchBy(), qaSearchDto.getSearchQuery()))
						.orderBy(QQa.qa.id.desc())
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();
		
		long total = queryFactory.select(Wildcard.count).from(QQa.qa)
				.where(regDtsAfter(qaSearchDto.getSearchDateType()),
						searchByLike(qaSearchDto.getSearchBy(), qaSearchDto.getSearchQuery()))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
		
	}
	
	//검색어가 빈 문자열일때 
	private BooleanExpression qaNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ?
				null : QQa.qa.qaNm.like("%" + searchQuery + "%");
	}
	
	
}
