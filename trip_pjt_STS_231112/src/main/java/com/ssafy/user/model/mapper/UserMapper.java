package com.ssafy.user.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.user.model.UserDto;

@Mapper
public interface UserMapper {
	int idCheck(String user_id) throws SQLException;
	void joinUser(UserDto userDto) throws SQLException;
	UserDto loginUser(UserDto userDto) throws SQLException;
//	UserDto findPassword(String user_name, String user_id) throws SQLException;
	String findPassword(String user_name, String user_id) throws SQLException;
	
	//JWT
	UserDto userInfo(String user_id) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
	//관리자 Admin
	List<UserDto> listUser(Map<String, String> map) throws SQLException;
	UserDto getUser(String user_id) throws SQLException;
	void updateUser(UserDto userDto) throws SQLException;
	void deleteUser(String user_id) throws SQLException;
}
