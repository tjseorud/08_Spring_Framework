package com.kh.spring.member.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor	//의존성주입 생성자를 생성해주는 애노테이션
public class MemberController {
	//@Autowired 	//1번
	private final MemberService memberService;
	
	/*@Autowired	//2번
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}*/
	
	/*@Autowired		//3번(권장됨)
	public void MemberController(MemberService memberService) {
		this.memberService = memberService;
	}*/
	
	//1.
	/*@RequestMapping(value="login")
	public String login(HttpServletRequest request) {
//		System.out.println("히히");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		log.info("id :{} | pw :{}", id, pw);
		return "main_page";
	}*/
	//2.
	/*@RequestMapping("login")
	public String login(@RequestParam("id") String id, @RequestParam("pw") String pw) {
		log.info("id:{} | pw:{}", id, pw);
		return "main_page";
	}*/
	//3.
	/*@PostMapping("login")
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
	//핸들러 / 리쿼스트 핸들러

/*	@PostMapping("login")
	public String login(MemberDTO member, HttpSession session, Model model) {
//		log.info("{}", member);
		/* 데이터가공 => 패스
		 * 요청처리 =>
		 * 응답화면지정
		 *
		MemberDTO loginMember = memberService.login(member);
		 /* if(loginMember != null) {
			log.info("성공");
		} else {
			log.info("실패");
		}*
		if(loginMember != null) {	//성공시
			session.setAttribute("loginMember", loginMember);
			//sendRedirect
			return "redirect:/";
		} else {	//실패시
			//requestScope에 에러문구를 담아서 포워딩
			//스프링에서는 Model객체를 이용해 RequestScope에 값을 담음
			model.addAttribute("message", "로그인 실패");
			return "include/error_page";
		}
//		return "main_page";
	}*/
	//두 번쨰 방법봔환타입 ModelAndView로 돌아기기
	@PostMapping("login")
	public ModelAndView login(MemberDTO member, HttpSession session, ModelAndView mv) {
		MemberDTO loginMember = memberService.login(member);
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
		} else {
			mv.addObject("message", "로그인 실패").setViewName("include/error_page");
		}
		return mv;
	}
	
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session, ModelAndView mv) {
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("signup-form")
	public String singupForm() {
		//WEB-INF/views/ member/signup-form .jsp
		return "member/signup-form";
	}
	
	/**
	 * @param member id, pw, name, email
	 * @return 성공시 메인, 실패시 errpage
	 */
	@PostMapping("signup")
	public String join(MemberDTO member, HttpServletRequest request) {
		/*try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		//log.info("ALL {}",member);
		memberService.signUp(member);
		return "main_page";
	}
	
}
