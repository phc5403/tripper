package com.ssafy.trip.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SidoDto (시도 정보)", description = "(시도 코드, 시도 이름)을 가진 Domain Class")
public class SidoDto {
	@ApiModelProperty(value = "시도 코드")
	private int sido_code;
	
	@ApiModelProperty(value = "시도 이름")
	private String sido_name;
	
	public int getSido_code() {
		return sido_code;
	}
	public void setSido_code(int sido_code) {
		this.sido_code = sido_code;
	}
	public String getSido_name() {
		return sido_name;
	}
	public void setSido_name(String sido_name) {
		this.sido_name = sido_name;
	}
	@Override
	public String toString() {
		return "SidoDto [sido_code=" + sido_code + ", sido_name=" + sido_name + "]";
	}
	
	
	
}
