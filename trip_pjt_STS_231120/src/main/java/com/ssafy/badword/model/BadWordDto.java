package com.ssafy.badword.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel(value = "BadWordDto (비속어 필터 정보) ", description = "(순번, 필터링 할 비속어)를 가진 Domain Class")
public class BadWordDto {
	@ApiModelProperty(value ="비속어 번호")
	private int bad_no;
	@ApiModelProperty(value ="비속어")
	private String fword;
}
