package com.kh.spring.template.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.template.model.service.TemplateService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping(value = "template", produces = "application/json; charset=UTF-8")
@RequiredArgsConstructor
public class TemplateController {
	
	private final TemplateService templateService;
	
	@GetMapping
	public ResponseEntity<String> getTemplats(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo) {
		//log.info("pageNo : {}",pageNo);
		String responseData = templateService.requestGetTemplat(pageNo);
		
		return ResponseEntity.ok(responseData);		
	}
}
