package com.ssafy.notice.model.service;

import java.util.Map;

import com.ssafy.notice.model.NoticeDto;
import com.ssafy.notice.model.NoticeListDto;

public interface NoticeService {
		int writeNotice(NoticeDto noticeDto) throws Exception;
		NoticeListDto listNotice(Map<String, String> param) throws Exception;
		NoticeDto getNotice(int notice_no) throws Exception;
		void updateHit(int notice_no) throws Exception;
		
		int modifyNotice(NoticeDto noticeDto) throws Exception;
		void deleteNotice(int notice_no) throws Exception;
	
}
