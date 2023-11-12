package com.ssafy.trip.model.service;

import java.util.List;

import com.ssafy.trip.model.AttractionInfoDto;

public interface AttractionService {
	List<AttractionInfoDto> attractionList(int sido_code, int gugun_code) throws Exception;
	List<AttractionInfoDto> searchByTitle(String title, int sido_code, int content_id) throws Exception;
}
