package com.ssafy.badword.service;

import java.util.*;

import com.ssafy.badword.model.BadWordDto;

public interface BadWordService {
	// 비속어 목록 가져오기
	List<BadWordDto> getBadList() throws Exception;
	
	// 보이어-무어 알고리즘
	int boyerMoore(String str, String pattern) throws Exception;
	
}
