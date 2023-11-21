package com.ssafy.user.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
UserMapper userMapper;
	
	@Autowired
	public UserServiceImpl(UserMapper userMapper) {
		super();
		this.userMapper = userMapper;
	}

	@Override
	public int idCheck(String user_id) throws Exception {
		return userMapper.idCheck(user_id);
	}

	@Override
	public void joinUser(UserDto memberDto) throws Exception {
		userMapper.joinUser(memberDto);
	}

	@Override
	public UserDto loginUser(UserDto userDto) throws Exception {
		return userMapper.loginUser(userDto);
	}

	@Override
	public List<UserDto> listUser(Map<String, String> map) throws Exception {
		return userMapper.listUser(map);
	}

	@Override
	public UserDto getUser(String user_id) throws Exception {
		return userMapper.getUser(user_id);
	}

	@Override
	public void updateUser(UserDto memberDto) throws Exception {
		userMapper.updateUser(memberDto);
	}

	@Override
	public void deleteUser(String user_id) throws Exception {
		userMapper.deleteUser(user_id);
	}

	@Override
	public UserDto userInfo(String user_id) throws Exception {
		 return userMapper.userInfo(user_id);
	}

	@Override
	public void saveRefreshToken(String user_id, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("token", refreshToken);
		userMapper.saveRefreshToken(map);
		
	}

	@Override
	public Object getRefreshToken(String user_id) throws Exception {
		return userMapper.getRefreshToken(user_id);
	}

	@Override
	public void deleRefreshToken(String user_id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("token", null);
		userMapper.deleteRefreshToken(map);
		
	}


	@Override
	public String findPassword(String user_name, String user_id) throws Exception {
		return userMapper.findPassword(user_name, user_id);
	}
}
