package com.ssafy.trip.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.model.AttractionInfoDto;
import com.ssafy.trip.model.AttractionListDto;

public interface AttractionService {
//	List<AttractionInfoDto> attractionList(int sido_code, int gugun_code) throws Exception;
//	AttractionListDto attractionList(int sido_code, int gugun_code, Map<String, String> map) throws Exception;
	AttractionListDto attractionList(Map<String, String> map) throws Exception;


	List<AttractionInfoDto> searchByTitle(String title, int sido_code, int content_id) throws Exception;
}
