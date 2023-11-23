package com.ssafy.notice.model;

import java.util.List;

import com.ssafy.board.model.FileInfoDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@ApiModel(value = "NoticeDto (공지사항 정보)", description = "(글번호, 작성자ID, 제목, 내용, 조회수, 작성시간)을 가진 Domain Class")
public class NoticeDto {
	@ApiModelProperty(value = "게시글 번호")
//	@JsonProperty(value="notice_no")
	private int notice_no;

	@ApiModelProperty(value = "회원 아이디")
//	@JsonProperty(value="user_id")
	private String user_id;
	
	@ApiModelProperty(value = "작성자 이름")
//	@JsonProperty(value="user_name")
	private String user_name;
	
	@ApiModelProperty(value = "게시글 제목")
//	@JsonProperty(value="board_title")
	private String notice_title;
	
	@ApiModelProperty(value = "게시글 내용")
//	@JsonProperty(value="board_content")
	private String notice_content;
	
	@ApiModelProperty(value = "게시글 조회수")
//	@JsonProperty(value="board_hit")
	private int notice_hit;
	
	@ApiModelProperty(value = "게시글 작성 시간")
//	@JsonProperty(value="board_write_time")
	private String notice_write_time;
	
	@ApiModelProperty(value = "업로드 파일정보")
//	@JsonProperty(value="fileInfos")
	private List<FileInfoDto> fileInfos;
	
	@ApiModelProperty(value = "작성자 프로필")
//	@JsonProperty(value = "user_img")
	private String user_img;
	
}