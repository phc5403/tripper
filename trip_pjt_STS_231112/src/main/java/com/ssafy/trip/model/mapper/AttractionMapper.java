package com.ssafy.trip.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.trip.model.AttractionInfoDto;

@Mapper
public interface AttractionMapper {
	List<AttractionInfoDto> attractionList(int sido_code, int gugun_code) throws SQLException;
	List<AttractionInfoDto> searchByTitle(@Param("title") String title, @Param("sido_code") int sido_code, @Param("content_id") int content_id) throws Exception;
}
