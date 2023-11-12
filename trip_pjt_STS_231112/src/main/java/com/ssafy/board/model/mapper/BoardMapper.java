package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.FileInfoDto;

@Mapper
public interface BoardMapper {
	// 게시판 CRUD
	void writeArticle(BoardDto boardDto) throws SQLException;
	List<BoardDto> listArticle(Map<String, Object> param) throws SQLException;
	BoardDto getArticle(int board_no) throws SQLException;
	void updateHit(int board_no) throws SQLException;
	void modifyArticle(BoardDto boardDto) throws SQLException;
	void deleteArticle(int board_no) throws SQLException;
	
	// 파일 업로드
	void registerFile(BoardDto boardDto) throws Exception;
	void deleteFile(int board_no) throws Exception;
	List<FileInfoDto> fileInfoList(int board_no) throws Exception;

	// 페이지 네이션
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;

}
