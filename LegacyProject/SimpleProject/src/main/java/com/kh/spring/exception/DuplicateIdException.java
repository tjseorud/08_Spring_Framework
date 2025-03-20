package com.kh.spring.member.model.service;

public class DuplicateIdException extends RuntimeException {

	public DuplicateIdException(String message) {
		super(message);
	}
	
}
