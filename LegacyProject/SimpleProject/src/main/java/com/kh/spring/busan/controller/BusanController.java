package com.kh.spring.busan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.busan.model.service.BusanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping(value = "busans", produces = "application/json; charset=UTF-8")
@RequiredArgsConstructor
public class BusanController {
	
	private final BusanService busanService;
	
	@GetMapping
	public ResponseEntity<String> getBusanFoods(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo) {
		//log.info("pageNo : {}",pageNo);
		String responseData = busanService.requestGetBusan(pageNo);
		
		return ResponseEntity.ok(responseData);
	}
	
}
