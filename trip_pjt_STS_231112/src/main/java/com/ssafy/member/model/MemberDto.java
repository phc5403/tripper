package com.ssafy.member.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(value = "MemberDto (회원정보)", description = "아이디, 비밀번호, 이름을 가진 Domain Class")
public class MemberDto {
	@ApiModelProperty(value = "회원아이디")
	private String user_id;
	
	@ApiModelProperty(value = "회원이름")
	private String user_name;
	
	@ApiModelProperty(value = "회원비밀번호")
	private String user_pwd;
	
	@ApiModelProperty(value = "이메일아이디")
	private String user_email_id;
	
	@ApiModelProperty(value = "이메일도메인")
	private String user_email_domain;
	
	@ApiModelProperty(value = "가입일", hidden = true)
	private String user_join_date;
	
	@ApiModelProperty(value = "계정 상태", hidden = true)
	private String user_status;

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

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public String getUser_email_id() {
		return user_email_id;
	}

	public void setUser_email_id(String user_email_id) {
		this.user_email_id = user_email_id;
	}

	public String getUser_email_domain() {
		return user_email_domain;
	}

	public void setUser_email_domain(String user_email_domain) {
		this.user_email_domain = user_email_domain;
	}

	public String getUser_join_date() {
		return user_join_date;
	}

	public void setUser_join_date(String user_join_date) {
		this.user_join_date = user_join_date;
	}

	public String getUser_status() {
		return user_status;
	}

	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}

	@Override
	public String toString() {
		return "MemberDto [user_id=" + user_id + ", user_name=" + user_name + ", user_pwd=" + user_pwd
				+ ", user_email_id=" + user_email_id + ", user_email_domain=" + user_email_domain + ", user_join_date="
				+ user_join_date + ", user_status=" + user_status + "]";
	}

	
}
