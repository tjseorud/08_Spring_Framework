package com.kh.spring.busan.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.busan.model.dto.Comment;
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
	
	//2절 상세조회
	@GetMapping("/{id}")
	public ResponseEntity<?> getBusanDetail(@PathVariable(name = "id") int id) {
		//log.info("id : {}",id);
		String response = busanService.requestGetBusanDetail(id);
		return ResponseEntity.ok(response);
	}
	
	//3절 식당에 댓글달기 및 조회
	@PostMapping("/comments")	//식당번호, 내용 => 가공
	public ResponseEntity<?> save(@RequestBody Comment comment){
		//log.info("comment : {}",comment);
		busanService.saveComment(comment);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/comments/{id}")
	public ResponseEntity<List<Comment>> getComments(@PathVariable(name = "id") Long id){
		log.info("comments id : {}",id);
		List<Comment> comment = busanService.selectCommentList(id);
		return ResponseEntity.ok(comment);		
	}
	
	//4절 부트로 바꿔서 돌리기
}
