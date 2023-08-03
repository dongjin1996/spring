package com.fastfood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.fastfood.dto.CartDto;
import com.fastfood.dto.CartItemDto;
import com.fastfood.dto.CartListDto;
import com.fastfood.entity.Cart;
import com.fastfood.entity.CartItem;
import com.fastfood.entity.Item;
import com.fastfood.entity.ItemImg;
import com.fastfood.entity.Member;
import com.fastfood.repository.CartRepository;
import com.fastfood.repository.ItemImgRepository;
import com.fastfood.repository.ItemRepository;
import com.fastfood.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class Cartservice {
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final CartRepository cartRepository;
	private final ItemImgRepository itemImgRepository;
	
	//장바구니 넣기
	public Long cart(CartDto cartDto, String email) {
		
		//1.장바구니 넣을 상품 조회
		Item item = itemRepository.findById(cartDto.getItemId())
								  .orElseThrow(EntityNotFoundException::new);
		
		//2. 현재 로그인한 회원의 이메일을 이용해 회원정보를 조회
		Member member = memberRepository.findByEmail(email);
		
		//3.장바구니에 넣을 상품 엔티티와 수량을 이용하여 장바구니 상품 엔티티를 생성
		List<CartItem> cartItemList = new ArrayList<>();
		CartItem cartItem = CartItem.createCartItem(item, cartDto.getCount());
		cartItemList.add(cartItem);
		
		//4.회원 정보와 주문할 상품 리스트 정보를 이용하여 장바구니 엔티티를 생성
		com.fastfood.entity.Cart cart = com.fastfood.entity.Cart.createCart(member, cartItemList);
		cartRepository.save(cart); //insert
		
		return cart.getId();
	}
	
	//카트 목록 가져오는 서비스
	@Transactional(readOnly = true)
	public Page<CartListDto> getCartList(String email, Pageable pageable) {
		
		//1.유저 아이디와 페이징 조건을 이용하여 주문 목록 조회
		List<com.fastfood.entity.Cart> carts = cartRepository.findCarts(email, pageable);
		
		//2.유저의 카트 총 개수를 구한다
		Long totalCount = cartRepository.countCart(email);
		
		List<CartListDto> cartListDtos = new ArrayList<>();
		
		//3. 카트 리스트를 순회하면서 카트페이지에 전달할 DTO 생성
		for (com.fastfood.entity.Cart cart : carts) {
			CartListDto cartListDto = new CartListDto(cart);
			List<CartItem> cartItems = cart.getCartItems();
			
			for(CartItem cartItem : cartItems) {
				//상품의 대표 이미지
				ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(cartItem.getItem().getId(), "Y");
				CartItemDto cartItemDto = new CartItemDto(cartItem, itemImg.getImgUrl());
				cartListDto.addCartItemDto(cartItemDto);
			}
			
			cartListDtos.add(cartListDto);
		}
		
		return new PageImpl<>(cartListDtos, pageable, totalCount);
	}
	
	@Transactional(readOnly = true)
	public boolean validateCart (Long cartId, String email) {
		Member curMember = memberRepository.findByEmail(email); //로그인한 사용자 찾기
		com.fastfood.entity.Cart cart = cartRepository.findById(cartId)
					.orElseThrow(EntityNotFoundException::new);
		
		Member savedMember = cart.getMember(); //장바구니에 넣은 사용자 찾기
		
		//로그인한 사용자의 이메일과 주문한 사용자의 이메일이 같은지 최종 비교
		if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
			return false;
		}
		
		return true;
	}
	
	//카트삭제
	public void deleteCartItem(Long cartItemId) {
		Cart cart = cartRepository.findById(cartItemId)
									.orElseThrow(EntityNotFoundException::new);
		
		cartRepository.delete(cart);
	}
}
