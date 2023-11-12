package com.ssafy.trip.model.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.ssafy.trip.model.AttractionInfoDto;
import com.ssafy.trip.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService {

	AttractionMapper attractionMapper;
	
	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}

	@Override
	public List<AttractionInfoDto> attractionList(int sido_code, int gugun_code) throws Exception {
		return attractionMapper.attractionList(sido_code, gugun_code);
	}

	@Override
	public List<AttractionInfoDto> searchByTitle(@Param("title") String title, @Param("sido_code") int sido_code, @Param("content_id") int content_id) throws Exception {
		return attractionMapper.searchByTitle(title, sido_code, content_id);
	}
	
}
