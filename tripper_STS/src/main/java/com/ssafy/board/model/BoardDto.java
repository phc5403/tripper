package com.ssafy.board.model;

import java.util.List;

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
@ApiModel(value = "BoardDto (게시판 정보)", description = "(게시글번호, 작성자 ID, 제목, 내용, 조회수, 작성시간)을 가진 Domain Class")
public class BoardDto {
	@ApiModelProperty(value = "게시글 번호")
//	@JsonProperty(value="board_no")
	private int board_no;

	@ApiModelProperty(value = "회원 아이디")
//	@JsonProperty(value="user_id")
	private String user_id;
	
	@ApiModelProperty(value = "작성자 이름")
//	@JsonProperty(value="user_name")
	private String user_name;
	
	@ApiModelProperty(value = "게시글 제목")
//	@JsonProperty(value="board_title")
	private String board_title;
	
	@ApiModelProperty(value = "게시글 내용")
//	@JsonProperty(value="board_content")
	private String board_content;
	
	@ApiModelProperty(value = "게시글 조회수")
//	@JsonProperty(value="board_hit")
	private int board_hit;
	
	@ApiModelProperty(value = "게시글 작성 시간")
//	@JsonProperty(value="board_write_time")
	private String board_write_time;
	
	@ApiModelProperty(value = "업로드 파일정보")
//	@JsonProperty(value="fileInfos")
	private List<FileInfoDto> fileInfos;
	
	
}
