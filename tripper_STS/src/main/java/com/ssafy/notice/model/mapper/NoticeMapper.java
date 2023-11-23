package com.ssafy.notice.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.notice.model.NoticeDto;

@Mapper
public interface NoticeMapper {
	// 게시판 CRUD
	void writeNotice(NoticeDto noticeDto) throws SQLException;
	List<NoticeDto> listNotice(Map<String, Object> param) throws SQLException;
	NoticeDto getNotice(int notice_no) throws SQLException;
	void updateHit(int notice_no) throws SQLException;
	void modifyNotice(NoticeDto noticeDto) throws SQLException;
	void deleteNotice(int notice_no) throws SQLException;

	// 페이지 네이션
	int getTotalNoticeCount(Map<String, Object> param) throws SQLException;
}
