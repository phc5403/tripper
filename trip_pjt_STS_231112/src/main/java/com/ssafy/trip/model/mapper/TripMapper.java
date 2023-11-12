package com.ssafy.trip.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.TripDto;

@Mapper
public interface TripMapper {
	List<TripDto> getsSidoList() throws SQLException;
	List<TripDto> getsGugunList(int sido_code) throws SQLException;
}
