package com.fastfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import com.fastfood.config.EncryptionUtils;
import com.fastfood.dto.MailDto;
import com.fastfood.repository.MemberRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class SendEmailService {
	
	
	@Autowired
	MemberRepository memberRepository;
	
	private JavaMailSender mailSender;
	private static final String FROM_ADDRESS = "본인의 이메일 주소를 입력해주세요.";
	
	
	public MailDto createMailAndChangePassword(String email, String name) {
		String str = getTempPassword();
		MailDto dto = new MailDto();
		dto.setAddress(email);
		dto.setTitle(name+ "님의 FastFood 임시비밀번호 안내 이메일 입니다.");
		dto.setMessage("안녕하세요. FastFood 임시비밀번호 안내 관련 이메일 입니다." + "["+ name +"]" + "님의 임시비밀번호는"+ str + "입니다.");
		updatePassword(str, email);
		return dto;
	}
	
	public void updatePassword(String str, String email) {
		String password = EncryptionUtils.encryptMD5(str);
		long id = memberRepository.findByEmail(email).getId();
		memberRepository.updateUserPassword(id, password);
	}
	
	 public String getTempPassword(){
	        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
	                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	        String str = "";

	        int idx = 0;
	        for (int i = 0; i < 10; i++) {
	            idx = (int) (charSet.length * Math.random());
	            str += charSet[idx];
	        }
	        return str;
	    }
	 
	 public void mailSend(MailDto mailDto) {
		 System.out.println("이메일 전송 완료");
		 SimpleMailMessage message = new SimpleMailMessage();
		 message.setTo(mailDto.getAddress());
		 message.setFrom(SendEmailService.FROM_ADDRESS);
		 message.setSubject(mailDto.getTitle());
		 message.setText(mailDto.getMessage());
	 }
}
