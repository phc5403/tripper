package com.ssafy.trip.model.service;

import java.util.List;

import com.ssafy.trip.model.TripDto;

public interface TripService {
	List<TripDto> getsSidoList() throws Exception;
	List<TripDto> getsGugunList(int sido_code) throws Exception;
}
