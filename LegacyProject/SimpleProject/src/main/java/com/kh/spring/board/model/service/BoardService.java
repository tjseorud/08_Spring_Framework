package com.kh.spring.board.model.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;

public interface BoardService {
	
	//게시글 작성(파일첨부)	
	void insertBoard(BoardDTO board, MultipartFile file, HttpSession session);
	/*
	 * insertBoard();
	 * save();
	 */
	
	//게시글 목록조회
	Map<String, Object> selectBoardList(int currentPage);
	/*
	 * selectBoardList();
	 * selectAll();
	 * findAll();
	 */
	
	//게시글 상세보기(댓글도 같이조회) -->new 기술 써야지
	BoardDTO selectBoard(int boardNo);
	/*
	 * selectBoard();
	 * findBy???();
	 */
	
	//게시글 수정
	BoardDTO updateBoard(BoardDTO board, MultipartFile file);
	/*
	 * updateBoard();
	 * updateBy???();
	 */
	
	//게시글 삭제(STATUS 업데이트 하기 Y => N)
	void deleteBoard(int boardNo);

	
	//게시글 검색
	Map<String, Object> doSearch(Map<String, String> map);
	
	
	
	
}
