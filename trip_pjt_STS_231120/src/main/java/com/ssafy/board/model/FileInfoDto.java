package com.ssafy.board.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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
@ApiModel(value = "FileInfoDto : 업로드 파일정보", description = "게시글에 업로드한 파일의 정보를 나타낸다.")
public class FileInfoDto {

	@ApiModelProperty(value = "저장폴더")
//	@JsonProperty(value="saveFolder")
	private String saveFolder;
	
	@ApiModelProperty(value = "원본 파일이름")
//	@JsonProperty(value="originalFile")
	private String originalFile;
	
	@ApiModelProperty(value = "저장 파일이름")
//	@JsonProperty(value="saveFile")
	private String saveFile;
	

}
