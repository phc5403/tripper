package com.ssafy.trip.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "TripDto (시도, 구군 정보)", description = "(시도, 구군 코드와 이름)을 가진 Domain Class")
public class TripDto {
	@ApiModelProperty(value = "시도 코드")
	private int sido_code;
	
	@ApiModelProperty(value = "구군 코드")
	private int gugun_code;
	
	@ApiModelProperty(value = "시도 이름")
	private String sido_name;
	
	@ApiModelProperty(value = "구군 이름")
	private String gugun_name;
	
	public int getSido_code() {

		return sido_code;
	}
	public void setSido_code(int sido_code) {
		this.sido_code = sido_code;
	}
	public int getGugun_code() {
		return gugun_code;
	}
	public void setGugun_code(int gugun_code) {
		this.gugun_code = gugun_code;
	}
	public String getSido_name() {
		return sido_name;
	}
	public void setSido_name(String sido_name) {
		this.sido_name = sido_name;
	}
	public String getGugun_name() {
		return gugun_name;
	}
	public void setGugun_name(String gugun_name) {
		this.gugun_name = gugun_name;
	}
	@Override
	public String toString() {
		return "TripDto [sido_code=" + sido_code + ", gugun_code=" + gugun_code + ", sido_name=" + sido_name
				+ ", gugun_name=" + gugun_name + "]";
	}

	
}
