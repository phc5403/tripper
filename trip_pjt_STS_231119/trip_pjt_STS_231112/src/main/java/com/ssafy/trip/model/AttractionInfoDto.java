package com.ssafy.trip.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AttractionInfoDto (여행지별 상세 정보)", description = "(여행지 식별 번호, 관광지 종류, 관광지명, 전체 주소, 행정동, 우편번호, 전화번호, 대표 이미지, 서브 이미지, 방문 기록, 시도 코드, 구군 코드, 위도, 경도, mlevel)을 가진 Domain Class")
public class AttractionInfoDto {
	@ApiModelProperty(value = "여행지 식별 번호")
	private int content_id;
	
	@ApiModelProperty(value = "관광지 종류")
	private int content_type_id;
	
	@ApiModelProperty(value = "관광지명")
	private String title;
	
	@ApiModelProperty(value = "전체 주소")
	private String addr1;
	
	@ApiModelProperty(value = "행정동")
	private String addr2;
	
	@ApiModelProperty(value = "우편번호")
	private String zipcode;
	
	@ApiModelProperty(value = "전화번호")
	private String tel;
	
	@ApiModelProperty(value = "대표 이미지")
	private String first_image;
	
	@ApiModelProperty(value = "서브 이미지")
	private String first_image2;
	
	@ApiModelProperty(value = "방문 기록")
	private int readcount;
	
	@ApiModelProperty(value = "시도 코드")
	private int sido_code;
	
	@ApiModelProperty(value = "구군 코드")
	private int gugun_code;
	
	@ApiModelProperty(value = "위도")
	private double latitude;
	
	@ApiModelProperty(value = "경도")
	private double longitude;
	
	@ApiModelProperty(value = "mlevel")
	private String mlevel;

	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

	public int getContent_type_id() {
		return content_type_id;
	}

	public void setContent_type_id(int content_type_id) {
		this.content_type_id = content_type_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFirst_image() {
		return first_image;
	}

	public void setFirst_image(String first_image) {
		this.first_image = first_image;
	}

	public String getFirst_image2() {
		return first_image2;
	}

	public void setFirst_image2(String first_image2) {
		this.first_image2 = first_image2;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getMlevel() {
		return mlevel;
	}

	public void setMlevel(String mlevel) {
		this.mlevel = mlevel;
	}

	@Override
	public String toString() {
		return "AttractionInfoDto [content_id=" + content_id + ", content_type_id=" + content_type_id + ", title="
				+ title + ", addr1=" + addr1 + ", addr2=" + addr2 + ", zipcode=" + zipcode + ", tel=" + tel
				+ ", first_image=" + first_image + ", first_image2=" + first_image2 + ", readcount=" + readcount
				+ ", sido_code=" + sido_code + ", gugun_code=" + gugun_code + ", latitude=" + latitude + ", longitude="
				+ longitude + ", mlevel=" + mlevel + "]";
	}

}
