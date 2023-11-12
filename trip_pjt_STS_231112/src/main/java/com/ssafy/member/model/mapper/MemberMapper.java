package com.ssafy.member.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.model.MemberDto;

@Mapper
public interface MemberMapper {
	int idCheck(String user_id) throws SQLException;
	void joinUser(MemberDto memberDto) throws SQLException;
	MemberDto loginUser(Map<String, String> map) throws SQLException;
	MemberDto findPassword(String user_name, String user_id) throws SQLException;
//	String findPassword(String user_name, String user_id) throws SQLException;
	
	//관리자 Admin
	List<MemberDto> listUser(Map<String, String> map) throws SQLException;
	MemberDto getUser(String user_id) throws SQLException;
	void updateUser(MemberDto memberDto) throws SQLException;
	void deleteUser(String user_id) throws SQLException;
}
