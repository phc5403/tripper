package com.ssafy.trip.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.trip.model.AttractionInfoDto;
import com.ssafy.trip.model.AttractionListDto;
import com.ssafy.trip.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService {

	AttractionMapper attractionMapper;
	
	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}

//	@Override
//	public List<AttractionInfoDto> attractionList(int sido_code, int gugun_code) throws Exception {
//		return attractionMapper.attractionList(sido_code, gugun_code);
//	}
	
	@Override
	public AttractionListDto attractionList(int sido_code, int gugun_code, Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

//		String key = map.get("key");
//		param.put("key", key == null ? "" : key);
//		if ("user_id".equals(key))
//			param.put("key", key == null ? "" : "b.user_id");
		List<AttractionInfoDto> list = attractionMapper.attractionList(sido_code, gugun_code, param);
		System.out.println(list.toString());
		
//		if ("user_id".equals(key))
//			param.put("key", key == null ? "" : "user_id");
		int totalAttrCount = attractionMapper.getTotalAttractionCount(param);
		int totalPageCount = (totalAttrCount - 1) / sizePerPage + 1;
		
//		System.out.println(totalAttrCount);

		AttractionListDto attractionListDto = new AttractionListDto();
		attractionListDto.setAttractions(list);
		attractionListDto.setCurrentPage(currentPage);
		attractionListDto.setTotalPageCount(totalPageCount);

		return attractionListDto;
	}

	@Override
	public List<AttractionInfoDto> searchByTitle(@Param("title") String title, @Param("sido_code") int sido_code, @Param("content_id") int content_id) throws Exception {
		return attractionMapper.searchByTitle(title, sido_code, content_id);
	}
	
}
