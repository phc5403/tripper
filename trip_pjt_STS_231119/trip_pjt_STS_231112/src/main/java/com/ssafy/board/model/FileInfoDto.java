package com.ssafy.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "FileInfoDto : 업로드 파일정보", description = "게시글에 업로드한 파일의 정보를 나타낸다.")
public class FileInfoDto {

	@ApiModelProperty(value = "저장폴더")
	private String saveFolder;
	@ApiModelProperty(value = "원본 파일이름")
	private String originalFile;
	@ApiModelProperty(value = "저장 파일이름")
	private String saveFile;

	public String getSaveFolder() {
		return saveFolder;
	}

	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}

	public String getOriginalFile() {
		return originalFile;
	}

	public void setOriginalFile(String originalFile) {
		this.originalFile = originalFile;
	}

	public String getSaveFile() {
		return saveFile;
	}

	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}

	@Override
	public String toString() {
		return "FileInfoDto [saveFolder=" + saveFolder + ", originalFile=" + originalFile + ", saveFile=" + saveFile
				+ "]";
	}

}
