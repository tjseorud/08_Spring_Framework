package com.kh.spring.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AjaxController {
	
	@GetMapping("ajax")
	public String forward() {
		return "ajax/ajax";
	}
	
	/*
	 * 내가 반환하는 String타입의 값이 View의 정보가 아니다. 응답데이터다
	 *  =>MessageConverter로 이동하게끔
	 * @ResponseBody
	 */
	@ResponseBody
	@GetMapping(value = "test", produces = "text/html; charset=UTF-8")
	public String ajaxReturn(@RequestParam(name="input") String value) {
		log.info("AJAX 요청을 통해 넘어온 VALUE :{}",value);
		String returnValue = value.equals("admin") ? "아이디가 있어요": "아이디가 없어요";
		return returnValue;
	}
	
	
	//250325
	private final BoardService boardService;
	
	@Autowired
	public AjaxController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@ResponseBody
	@GetMapping(value="study", produces="application/json; charset=UTF-8")
	public ResponseEntity<BoardDTO> ajaxStudy(@RequestParam("replyNo") int boardNo) {
		BoardDTO board = boardService.selectBoard(boardNo);
		log.info("{}",board);
		return ResponseEntity.ok(board);
	}
}
