package com.ssafy.trip.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.model.TripDto;
import com.ssafy.trip.model.mapper.TripMapper;

@Service
public class TripServiceImpl implements TripService {
	
	TripMapper tripMapper;

	@Autowired
	public TripServiceImpl(TripMapper tripMapper) {
		super();
		this.tripMapper = tripMapper;
	}

	@Override
	public List<TripDto> getsSidoList() throws Exception {
		return tripMapper.getsSidoList();
	}

	@Override
	public List<TripDto> getsGugunList(int sido_code) throws Exception {
		return tripMapper.getsGugunList(sido_code);
	}

}
