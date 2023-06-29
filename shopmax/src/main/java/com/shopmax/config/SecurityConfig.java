package com.shopmax.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //bean 객체를 싱글톤으로 공유할 수 있게 해준다.
@EnableWebSecurity //spring security filterChain이 자동으로 포함되게 한다.
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//로그인에 대한 설정
		http.formLogin()
			.loginPage("/members/login") //로그인 화면 url 설정
			.defaultSuccessUrl("/") //로그인 성공하면 이동할 페이지 url 
			.usernameParameter("email") //로그인시 id로 사용할 파라메터 이름
			.failureUrl("members/login/error") //로그인 실패시 이동할 url
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	
	}
}
