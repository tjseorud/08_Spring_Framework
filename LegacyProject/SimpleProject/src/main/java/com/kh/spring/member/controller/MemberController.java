package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.member.model.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	/*
	@RequestMapping(value="login")
	public String login(HttpServletRequest request) {
//		System.out.println("히히");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		log.info("id :{} | pw :{}", id, pw);
		return "main_page";
	}*/
	/*
	@RequestMapping("login")
	public String login(@RequestParam("id") String id, @RequestParam("pw") String pw) {
		log.info("id:{} | pw:{}", id, pw);
		return "main_page";
	}*/
	/*
	@PostMapping("login")
	public String login(String id, String pw) {
		log.info("id:{} | pw:{}", id, pw);
		MemberDTO member = new MemberDTO();
		member.setMemberId(id);
		member.setMemberPw(pw);
		return "main_page";		
	}*/
	
	/**
	 * 커맨드 객체방식
	 * 1. 매개변수 자료형에 반드시 기본생성자가 존재할것
	 * 2. 전달되는 키값과 객체의 필드명이 동일할것
	 * 3. setter메서드가 반드시 존재할것
	 * 
	 * 스프링에서 해당객체를 기본생성자를 통해서 생성한후 내부적으로 setter메서드를 찾아서 요청시 전달값을 해당 필드에 대입해줌
	 * (setter Injection)
	 */
	@PostMapping("login")
	public String login(MemberDTO member) {
		log.info("{}", member);
		return "main_page";
	}	
}
