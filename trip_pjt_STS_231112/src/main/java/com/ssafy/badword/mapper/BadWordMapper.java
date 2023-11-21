package com.ssafy.badword.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.badword.model.BadWordDto;

@Mapper
public interface BadWordMapper {
	// 비속어 목록 가져오기
	List<BadWordDto> getBadList() throws SQLException;
	
	// 보이어-무어 알고리즘
//	int boyerMoore(String str, String pattern) throws SQLException;
}
