package com.kh.spring.exception.controller;

import java.security.InvalidParameterException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.PasswordNotMatchException;
import com.kh.spring.exception.TooLargeValueException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExcetionHandlingController {
	
	private ModelAndView createErrorResponse(String erroMsg, Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message",erroMsg).setViewName("include/error_page");
		log.info("발생 예외 : {}",erroMsg);
		return mv;
	}
	
	@ExceptionHandler(MemberNotFoundException.class)
	protected ModelAndView memberNotFoundError(MemberNotFoundException e) {		
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	protected ModelAndView invalidParameterError(InvalidParameterException e) {		
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView tooLargeValueError(TooLargeValueException e) {		
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(PasswordNotMatchException.class)
	protected ModelAndView passwordNotMatchError(PasswordNotMatchException e) {		
		return createErrorResponse(e.getMessage(), e);
	}
	
	
}
