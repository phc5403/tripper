package com.ssafy.member.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.member.model.MemberDto;

public interface MemberService {
	int idCheck(String user_id) throws Exception;
	void joinUser(MemberDto memberDto) throws Exception;
	MemberDto loginUser(Map<String, String> map) throws Exception;
	
//	memberDto findPassword(String user_name, String user_id) throws Exception;
//	String findPassword(String user_name, String user_id) throws Exception;
	
	//관리자 Admin
	List<MemberDto> listUser(Map<String, String> map) throws Exception;
	MemberDto getUser(String user_id) throws Exception;
	void updateUser(MemberDto memberDto) throws Exception;
	void deleteUser(String user_id) throws Exception;
}
