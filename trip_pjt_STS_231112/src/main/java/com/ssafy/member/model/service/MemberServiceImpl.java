package com.ssafy.member.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	MemberMapper memberMapper;
	
	@Autowired
	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}

	@Override
	public int idCheck(String user_id) throws Exception {
		return memberMapper.idCheck(user_id);
	}

	@Override
	public void joinUser(MemberDto memberDto) throws Exception {
		memberMapper.joinUser(memberDto);
	}

	@Override
	public MemberDto loginUser(Map<String, String> map) throws Exception {
		return memberMapper.loginUser(map);
	}

	@Override
	public List<MemberDto> listUser(Map<String, String> map) throws Exception {
		return memberMapper.listUser(map);
	}

	@Override
	public MemberDto getUser(String user_id) throws Exception {
		return memberMapper.getUser(user_id);
	}

	@Override
	public void updateUser(MemberDto memberDto) throws Exception {
		memberMapper.updateUser(memberDto);
	}

	@Override
	public void deleteUser(String user_id) throws Exception {
		memberMapper.deleteUser(user_id);
	}


//	@Override
//	public MemberDto findPassword(String user_name, String user_id) throws Exception {
//		return memberMapper.findPassword(user_name, user_id);
//	}
}
