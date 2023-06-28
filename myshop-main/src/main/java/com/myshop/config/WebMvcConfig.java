package com.myshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//업로드한 파일을 읽어올 경로를 설정
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	/* 
	 * WebMvcConfig 클래스에서 유독 프로퍼티값을 못읽어오는 에러가 발생한다. 
	스프링의 버그인듯 하다. 에러가 난다면 우선 아래와 같이 텍스트 고정값으로 넣어준다.
	*/
	//@Value("${uploadPath}") //프로퍼티의 값을 읽어온다.
	String uploadPath = "file:///C:/shop/";

	//웹 브라우저에 입력하는 url이 /images로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로 파일을 읽어온다.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**") 
		        .addResourceLocations(uploadPath);
	}
	
}
