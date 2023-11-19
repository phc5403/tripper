package com.ssafy.trip.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AttractionDescriptionDto (여행지 설명)", description = "(여행지 식별번호, 홈페이지, 설명, 전화번호)을 가진 Domain Class")
public class AttractionDescriptionDto {
	@ApiModelProperty(value = "여행지 식별번호")
	private int content_id;
	@ApiModelProperty(value = "홈페이지")
	private String homepage;
	@ApiModelProperty(value = "설명")
	private String overview;
	@ApiModelProperty(value = "전화번호")
	private String telname;

	
	public int getContent_id() {
		return content_id;
	}
	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getTelname() {
		return telname;
	}
	public void setTelname(String telname) {
		this.telname = telname;
	}
	@Override
	public String toString() {
		return "AttractionDescriptionDto [content_id=" + content_id + ", homepage=" + homepage + ", overview="
				+ overview + ", telname=" + telname + "]";
	}
	
	

}
