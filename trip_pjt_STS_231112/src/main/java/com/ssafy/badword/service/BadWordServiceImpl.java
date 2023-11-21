package com.ssafy.badword.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.badword.mapper.BadWordMapper;
import com.ssafy.badword.model.BadWordDto;

@Service
public class BadWordServiceImpl implements BadWordService {
	BadWordMapper badWordMapper;
	
	@Autowired
	public BadWordServiceImpl(BadWordMapper badWordMapper) {
		super();
		this.badWordMapper = badWordMapper;
	}

	@Override
	public List<BadWordDto> getBadList() throws Exception {
		return badWordMapper.getBadList();
	}

	@Override
	public int boyerMoore(String str, String pattern) throws Exception {
		int sdx;  // 본문의 위치 인덱스
		int pdx;  // 패턴의 위치 인덱스
		int strLen = str.length();
		int paLen = pattern.length();
		int[] skip = new int[Character.MAX_VALUE + 1];

		// 배열의 요소 초기화
		for (int i = 0; i < skip.length; i++)
			skip[i] = paLen ;
		// pattern에 있는 문자에 대해 table 값 변경
		for (int i = 0; i < paLen ; i++)
			skip[pattern.charAt(i)] = paLen  - 1 - i;
		
		// 뒤에서 부터 비교하기 때문에 초깃값을 패턴의 마지막 글자 위치로 설정.
		sdx = paLen  - 1;
		while (sdx < strLen) {  // 본문을 모두 탐색하는 동안 반복
			pdx = paLen  - 1;
			
			// 본문의 문자와 패턴의 문자가 같을 경우
			while (str.charAt(sdx) == pattern.charAt(pdx)) {
				if (pdx == 0)
					return sdx;  // 패턴이 일치하는 문자열의 시작 인덱스 반환
				sdx--;
				pdx--;
			}
			
			// 본문의 문자와 패턴의 문자가 다를 경우 점프
			sdx += (skip[str.charAt(sdx)] > paLen  - pdx) ? skip[str.charAt(sdx)] : paLen  - pdx;
		}
		// 본문안에 패턴이 없는 경우 -1 반환
		return -1;
//		return badWordMapper.boyerMoore(str, pattern);

	}

}
