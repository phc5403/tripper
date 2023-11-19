package com.ssafy.trip.model;

import java.util.List;

public class AttractionListDto {
	private List<AttractionInfoDto> attractions;
	private int currentPage;
	private int totalPageCount;
	
	public List<AttractionInfoDto> getAttractions() {
		return attractions;
	}
	public void setAttractions(List<AttractionInfoDto> attractions) {
		this.attractions = attractions;
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
		return "AttractionListDto [attractions=" + attractions + ", currentPage=" + currentPage + ", totalPageCount="
				+ totalPageCount + "]";
	} 
	
	
}
