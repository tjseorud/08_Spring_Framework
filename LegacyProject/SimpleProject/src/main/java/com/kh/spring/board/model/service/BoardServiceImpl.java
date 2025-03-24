package com.kh.spring.board.model.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.mapper.BoardMapper;
import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.reply.model.dto.ReplyDTO;
import com.kh.spring.util.model.dto.PageInfo;
import com.kh.spring.util.template.Pagination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;
	
	private void validateUser(BoardDTO board, HttpSession session) {
		//1.권한 체크
		MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
		
		if(loginMember != null && !loginMember.getMemberId().equals(board.getBoardWriter())) {
			throw new AuthenticationException("접근권한이 없습니다");
		}
	}
	
	private void valiContent(BoardDTO board) {
		//2.유효성 검사
		if(board.getBoardTitle() == null || board.getBoardTitle().trim().isEmpty() ||
			board.getBoardContent() == null || board.getBoardContent().trim().isEmpty() ||
			board.getBoardWriter() == null || board.getBoardWriter().trim().isEmpty() ) {
			throw new InvalidParameterException("유효하지 않은 요청입니다.");
		}
		//2_2
		/* xss 공격 방지 및 사용자가 원하는데로 출력
		 * <, >, \, &
		 */
		String boardTitle = board.getBoardTitle()
									.replaceAll("<", "&lt;")
									.replaceAll(">", "&gt;")
									.replaceAll("\n", "<br>");
		String boardContent = board.getBoardContent()
									.replaceAll("<", "&lt;")
									.replaceAll(">", "&gt;")
									.replaceAll("\n", "<br>");
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
	}
	
	private void transferFile(BoardDTO board, MultipartFile file, HttpSession session) {
		// 이름바꾸기 + 저장
		
		//이름 바꾸기
		//KH_현재시간+랜덤숫자+원본파일 확장자
		StringBuilder sb = new StringBuilder();
		sb.append("KH_");
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		//log.info("지금시간 :{}", currentTime);
		sb.append(currentTime);
		sb.append("_");
		
		int random = (int)(Math.random() * 900) + 100;
		sb.append(random);
		
		String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		sb.append(ext);			
		//log.info("바뀐 파일명 :{}",sb.toString());
		
		ServletContext application = session.getServletContext();
		String savePath = application.getRealPath("/resources/upload_files/");
		try {
			file.transferTo(new File(savePath + sb.toString()));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		board.setChangeName("/spring/resources/upload_files/"+ sb.toString());
		
	}
	
	@Override
	public void insertBoard(BoardDTO board, MultipartFile file, HttpSession session) {
		validateUser(board, session);	//1.권한 체크
		valiContent(board);		//2.유효성 검사
		
		//3 파일 유무체크
		if( !file.getOriginalFilename().isEmpty()) {
			transferFile(board, file, session);
		}		
		boardMapper.insertBoard(board);
	}

	@Override
	public Map<String, Object> selectBoardList(int currentPage) {
		List<BoardDTO> boards = new ArrayList<BoardDTO>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		int count = boardMapper.selectTotalCount();			
		PageInfo pi = Pagination.getPageInfo(count, currentPage, 5, 5);
		
		if(count != 0) {
			RowBounds rb = new RowBounds((currentPage - 1) * 5, 5);						
			boards = boardMapper.selectBoardList(rb);
		}
		map.put("boards", boards);
		map.put("pageInfo", pi);	//이거 주의 .put("pi", pi); => 이렇게 쓰면 오류남
		return map;
	}

	@Override
	public BoardDTO selectBoard(int boardNo) {
		/*BoardDTO board = boardMapper.selectBoard(boardNo);
		
		List<ReplyDTO> replyList = boardMapper.selectReply(boardNo);
		board.setReplyList(replyList);
		*/		
		BoardDTO board = boardMapper.selectBoardAndReply(boardNo);
		
		if(board == null) {
			throw new InvalidParameterException("존재하지 않는 게시글입니다.");
		}
		return board;
	}

	@Override
	public BoardDTO updateBoard(BoardDTO board, MultipartFile file) {
		return null;
	}

	@Override
	public void deleteBoard(int boardNo) {

	}

	@Override
	public Map<String, Object> doSearch(Map<String, String> map) {
		// map에서 get("condition") / get("keyword") 값이 비었나 안비었나 확인
		int searchedCount = boardMapper.searchedCount(map);	
//		log.info("count :{}",searchedCount);
		
		PageInfo pi = Pagination.getPageInfo(searchedCount, Integer.parseInt(map.get("currentPage")), 5, 5);		
		RowBounds rb = new RowBounds((pi.getCurrentPage() - 1) * 5, 5);
		
		List<BoardDTO> boards = boardMapper.selectSearchList(map, rb);	
		
		Map<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put("boards", boards);
		returnValue.put("pageInfo", pi);	//이거 주의 .put("pi", pi); => 이렇게 쓰면 오류남	
		
		return returnValue;
	}

}
