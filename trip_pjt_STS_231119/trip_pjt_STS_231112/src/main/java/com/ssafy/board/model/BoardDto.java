package com.ssafy.board.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "BoardDto (게시판 정보)", description = "(게시글번호, 작성자 ID, 제목, 내용, 조회수, 작성시간)을 가진 Domain Class")
public class BoardDto {
	@ApiModelProperty(value = "게시글 번호")
	private int board_no;

	@ApiModelProperty(value = "회원 아이디")
	private String user_id;
	
	@ApiModelProperty(value = "작성자 이름")
	private String user_name;
	
	@ApiModelProperty(value = "게시글 제목")
	private String board_title;
	
	@ApiModelProperty(value = "게시글 내용")
	private String board_content;
	
	@ApiModelProperty(value = "게시글 조회수", hidden = true)
	private int board_hit;
	
	@ApiModelProperty(value = "게시글 작성 시간", hidden = true)
	private String board_write_time;
	
	@ApiModelProperty(value = "업로드 파일정보")
	private List<FileInfoDto> fileInfos;

	public int getBoard_no() {
		return board_no;
	}

	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getBoard_title() {
		return board_title;
	}

	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}

	public String getBoard_content() {
		return board_content;
	}

	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}

	public int getBoard_hit() {
		return board_hit;
	}

	public void setBoard_hit(int board_hit) {
		this.board_hit = board_hit;
	}

	public String getBoard_write_time() {
		return board_write_time;
	}

	public void setBoard_write_time(String board_write_time) {
		this.board_write_time = board_write_time;
	}

	public List<FileInfoDto> getFileInfos() {
		return fileInfos;
	}

	public void setFileInfos(List<FileInfoDto> fileInfos) {
		this.fileInfos = fileInfos;
	}

	@Override
	public String toString() {
		return "BoardDto [board_no=" + board_no + ", user_id=" + user_id + ", user_name=" + user_name + ", board_title="
				+ board_title + ", board_content=" + board_content + ", board_hit=" + board_hit + ", board_write_time="
				+ board_write_time + ", fileInfos=" + fileInfos + "]";
	}
	
	
	
}
