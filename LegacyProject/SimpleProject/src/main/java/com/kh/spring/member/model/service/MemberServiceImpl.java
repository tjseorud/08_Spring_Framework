package com.kh.spring.member.model.service;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.PasswordNotMatchException;

import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	//@Autowired 
//	private final MemberDAO memberDAO;
	//@Autowired 
//	private final SqlSessionTemplate sqlSession;
	private final PasswordEncoder passwordEncoder;
	private final MemberValidator validator;	
	private final MemberMapper memberMapper;
	
	/*@Autowired 
	public MemberServiceImpl(MemberDAO memberDAO, SqlSessionTemplate sqlSession) {
		this.memberDAO = memberDAO;
		this.sqlSession = sqlSession;
	}*/	
	
	@Override
	public MemberDTO login(MemberDTO member) {
		//로그인이라는 요청을 처리해줘야하는데
		//아이디가 10자가 넘으면 안됨
		//아이디/비밀번호가 :빈문자열 or null이면 안됨	
		//1.Table에 아이디가 존재해야할듯
		//2.비밀번호가 일치해야할듯
		//3.둘다 통과면 정상적으로 조회할수 있도록
		validator.validatedLoginMember(member);		
		/*
		MemberDTO loginMember = memberMapper.login(member);
		//아이디만 일치해도 행의 정보를 필드에 담아옴
		//1. loginMember가 null 값과 돌일하다면 아이디가 존재하지 않음
		if(loginMember == null) {
			throw new MemberNotFoundException("존재하지 않는 아이디 입니다.");
		}*/
		//2. 아이디만 가지고 조회하기떄문에 비밀번호 검증후
		//	비밀번호가 유효하다면 회원정보를 session에 담기
		//	비밀번호가 유효하지않다면 비번이 이상한데??
		MemberDTO loginMember = validator.validateMemberExists(member);
		if(passwordEncoder.matches(member.getMemberPw(), loginMember.getMemberPw())) {
			return loginMember;
		} else {
			throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
		}
	}

	@Override
	public void signUp(MemberDTO member) {
		/*if(member == null || 
			member.getMemberId() == null ||
			member.getMemberId().trim().isEmpty() ||
			member.getMemberPw() == null ||
			member.getMemberPw().trim().isEmpty() ) {
			return;
		}*/
//		validator.validatedLoginMember(member);	//유효한가?
		/*int result = memberDAO.checkId(sqlSession, member.getMemberId());
		if(result > 0) {
			return;
		}*/
//		memberMapper.login(member);
		//암호화 하는법 .encode()호출
		//log.info("평문을 암호문으로 바꾼것 :{}",passwordEncoder.encode(member.getMemberPw()));
//		String encPwd = passwordEncoder.encode(member.getMemberPw());
		validator.validatedJoinMember(member);
		member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));
		memberMapper.signUp(member);
		/*if(consequence > 0) {
			return;
		} else {
			return;
		}*/
	}

	@Override
	public void update(MemberDTO member, HttpSession session) {
		MemberDTO sessionMember = ((MemberDTO)session.getAttribute("loginMember"));
		//사용자검증
		if(sessionMember == null) {
			throw new NullPointerException("빈칸이 있어요");
		}
		if( !member.getMemberId().equals(sessionMember.getMemberId())){
			throw new AuthenticationException("권한이 없습니다");
		}
		//존재하는 아이디인지 검증
		validator.validateMemberExists(member);
		int result = memberMapper.update(member);
		//SQL문 수행결과 검증
		if(result != 1) {
			throw new AuthenticationException("뭔진 모르지만 문제가 있으니 다시 시도해주세요.");
		}
		sessionMember.setMemberName(member.getMemberName());
		sessionMember.setEmail(member.getEmail());
	}

	@Override
	public void delete(MemberDTO member, HttpSession session) {
		MemberDTO sessionMember = ((MemberDTO)session.getAttribute("loginMember"));
		if( !member.getMemberId().equals(sessionMember.getMemberId())){
			throw new AuthenticationException("권한이 없습니다");
		}
		if(passwordEncoder.matches(member.getMemberPw(), sessionMember.getMemberPw())) {
			memberMapper.delete(member);
		} else {
			throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
		}
		
	}
	
}
