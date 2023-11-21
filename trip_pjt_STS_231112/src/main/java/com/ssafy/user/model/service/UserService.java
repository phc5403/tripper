package com.ssafy.user.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.user.model.UserDto;

public interface UserService {
	int idCheck(String user_id) throws Exception;
	void joinUser(UserDto userDto) throws Exception;
	UserDto loginUser(UserDto userDto) throws Exception;
	
	//JWT
	UserDto userInfo(String user_id) throws Exception;
	void saveRefreshToken(String user_id, String refreshToken) throws Exception;
	Object getRefreshToken(String user_id) throws Exception;
	void deleRefreshToken(String user_id) throws Exception;
	
//	memberDto findPassword(String user_name, String user_id) throws Exception;
	String findPassword(String user_name, String user_id) throws Exception;
	
	//관리자 Admin
	List<UserDto> listUser(Map<String, String> map) throws Exception;
	UserDto getUser(String user_id) throws Exception;
	void updateUser(UserDto userDto) throws Exception;
	void deleteUser(String user_id) throws Exception;
}
