package com.ssafy.trip.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "지역기반 관광정보파라미터 타입에 따라서 제목순,수정일순,등록일순 정렬검색목록을 조회하는 기능", description = "(여행지 식별 번호, 대분류 코드, 중분류 코드, 소분류 코드, 등록 일시, 수정 일시, booktour)을 가진 Domain Class")
public class AttractionDetailDto {
	@ApiModelProperty(value = "여행지 식별 번호")
	private int content_id;
	
	@ApiModelProperty(value = "대분류 코드")
	private String cat1;
	
	@ApiModelProperty(value = "중분류 코드")
	private String cat2;
	
	@ApiModelProperty(value = "소분류 코드")
	private String cat3;
	
	@ApiModelProperty(value = "등록 일시")
	private String createdTime;
	
	@ApiModelProperty(value = "수정 일시")
	private String modifiedTime;
	
	@ApiModelProperty(value = "booktour")
	private String booktour;
	
	public int getContent_id() {
		return content_id;
	}
	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}
	public String getCat1() {
		return cat1;
	}
	public void setCat1(String cat1) {
		this.cat1 = cat1;
	}
	public String getCat2() {
		return cat2;
	}
	public void setCat2(String cat2) {
		this.cat2 = cat2;
	}
	public String getCat3() {
		return cat3;
	}
	public void setCat3(String cat3) {
		this.cat3 = cat3;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getBooktour() {
		return booktour;
	}
	public void setBooktour(String booktour) {
		this.booktour = booktour;
	}
	@Override
	public String toString() {
		return "AttractionDetailDto [content_id=" + content_id + ", cat1=" + cat1 + ", cat2=" + cat2 + ", cat3=" + cat3
				+ ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime + ", booktour=" + booktour + "]";
	}

	
}
