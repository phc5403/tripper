package com.ssafy.trip.model.service;


import java.util.List;
import java.util.Map;

import com.ssafy.trip.model.AttractionInfoDto;
import com.ssafy.trip.model.AttractionListDto;

public interface AttractionService {
//	AttractionListDto attractionList(Map<String, String> map) throws Exception;
	
	AttractionListDto attractionList(Map<String, String> map, String choiceContent) throws Exception;


	List<AttractionInfoDto> searchByTitle(String title, int sido_code, int content_id) throws Exception;
	
	List<AttractionInfoDto> getPopularList() throws Exception;
	
	List<AttractionInfoDto> getStoreList() throws Exception;
}
