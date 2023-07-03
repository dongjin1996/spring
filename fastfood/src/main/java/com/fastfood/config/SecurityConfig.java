package com.fastfood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/css/**", "/js/**", "/img/**","/images/**","/fonts/**").permitAll()
				.requestMatchers("/", "/member/**", "/item/**").permitAll()
				.requestMatchers("/favicon.ico", "/error").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN") //"admin"으로 시작하는 경로는 어드민만 접속가능
				.anyRequest().authenticated() //그 외의 페이지는 모두 로그인 인증 받아야함
				)
		.formLogin(formLogin -> formLogin  //2. 로그인 관련된 설정
				.loginPage("/member/login")
				.defaultSuccessUrl("/") //로그인 성공 했을때 이동하는 페이지
				.usernameParameter("email") //로그인시 아이디로 사용할 파라메터이름
				.failureUrl("/member/login/error") //로그인 실패시 이동할 URL
				)
		.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) //로그아웃시 이동할 URL
				.logoutSuccessUrl("/") //로그아웃 성공시 이동할 URL
				)
		.exceptionHandling(handling -> handling  //4. 인증되지 않은 사용자가 리소스에 접근했을때 설정
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				)
		.rememberMe(Customizer.withDefaults());
		
		return http.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
