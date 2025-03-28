package com.kh.spring.busan.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.spring.busan.model.dto.Comment;

@Mapper
public interface CommentMapper {
	
	@Insert("INSERT INTO TB_COMMENTS VALUES(#{ content }, SYSDATE, #{ seq })")
	void saveComment(Comment comment);
	
	@Select("SELECT UC_SEQ seq, CONTENT, CREATE_DATE createDate FROM TB_COMMENTS WHERE UC_SEQ = #{ seq } ORDER BY CREATE_DATE DESC")
	List<Comment> selectCommentList(Long seq);
}
