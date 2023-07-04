package com.fastfood.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners (value = {AuditingEntityListener.class})
public abstract class BaseTimeEntity {
	
	@CreatedDate //엔티티가 생성돼서 저장될떄 시간을 자동으로 저장
	@Column(updatable = false) //컬럼의 값을 수정하지 못하게 막음
	private LocalDateTime regTime; //등록날짜
	
	@LastModifiedDate //수정될떄 시간을 자동으로 저장한다.
	private LocalDateTime updateTime; //수정날짜
}
