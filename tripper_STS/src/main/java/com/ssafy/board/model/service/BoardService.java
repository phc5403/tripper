package com.ssafy.board.model.service;

import java.util.Map;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;

public interface BoardService {
	int writeArticle(BoardDto boardDto) throws Exception;
	BoardListDto listArticle(Map<String, String> map) throws Exception;
	BoardDto getArticle(int board_no) throws Exception;
	void updateHit(int board_no) throws Exception;
	
	int modifyArticle(BoardDto boardDto) throws Exception;
	void deleteArticle(int board_no) throws Exception;
}
