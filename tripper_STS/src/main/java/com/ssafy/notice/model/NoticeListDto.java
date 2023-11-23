package com.ssafy.notice.model;

import java.util.List;

public class NoticeListDto {

	private List<NoticeDto> notices;
	private int currentPage;
	private int totalPageCount;
	
	public List<NoticeDto> getNotices() {
		return notices;
	}
	public void setNotices(List<NoticeDto> notices) {
		this.notices = notices;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	
	@Override
	public String toString() {
		return "NoticeListDto [notices=" + notices + ", currentPage=" + currentPage + ", totalPageCount="
				+ totalPageCount + "]";
	}
	
	
}
