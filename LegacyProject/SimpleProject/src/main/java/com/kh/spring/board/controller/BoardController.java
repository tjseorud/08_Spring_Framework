package com.kh.spring.board.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.exception.InvalidParameterException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	//?page=1
	@GetMapping("boards")
	public String toBoardList(@RequestParam(name="page", defaultValue="1") int page, Model model) {
		//한페이지에 보여주기 == 5
		//페이지 버튼 == 5
		//총 게시글 개수 == SELECT COUNT(*) FROM TB_SPRING_BOARD WHERE STATUS = 'Y'
		if(page < 1) {
			throw new InvalidParameterException("여긴 어디?");
		}
		Map<String, Object> map = boardService.selectBoardList(page);
		model.addAttribute("map", map);
		return "board/board_list";
	}
	
	@GetMapping("form.bo")
	public String goToForm() {
		return "board/insert_board";
	}
	
	@PostMapping("boards")
	public ModelAndView newBoard(ModelAndView mv, BoardDTO board, MultipartFile upfile, HttpSession session) {
		log.info("Date :{} | File date :{}", board, upfile);
		//첨부파일의 존재유무 => 차이점 => MultipartFile타입의 filename필드값으로 확인하겠다
		
		//INSERT INTO TB_SPRING_BOARD(BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER, CHANGE_NAME)
		//VALUES(#{boardTitle}, #{boardContent}, #{boardWriter}, #{changeName})
		//1. 권한있는 요청?
		//2. 값들이 유효성이 있는 값인가?
		//3. 전달된 파일이 존재하면 파일명 수정 서버에 올리고 BoardDTO의 changeName필드에 값을 대입
		boardService.insertBoard(board, upfile, session);
		mv.setViewName("redirect:boards");
		session.setAttribute("message", "게시글 등록 성공 ✅");
		return mv;
	}
	
	@GetMapping("boards/{id}")
	public ModelAndView goBoard(@PathVariable(name="id") int boardNo, ModelAndView mv) {
		log.info("번호 : {}",boardNo);
		if(boardNo < 1) {
			throw new InvalidParameterException("여긴 어디야?");
		}
		BoardDTO board = boardService.selectBoard(boardNo);
		mv.addObject("board", board).setViewName("board/board_detail");
		return mv;
	}
}
