package com.ssafy.trip.model.service;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.trip.model.AttractionInfoDto;
import com.ssafy.trip.model.AttractionListDto;
import com.ssafy.trip.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService {

	AttractionMapper attractionMapper;

	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}

	@Override
//	public AttractionListDto attractionList(Map<String, String> map) throws Exception {
	public AttractionListDto attractionList(Map<String, String> map, String choiceContent) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();

		System.out.println("==============AttractionServiceImpl===============");
		System.out.println(map);
		System.out.println("받은 choiceContent 길이 = " + choiceContent.length());
//		System.out.println(map.get("sido_code"));

		// 페이지 계산식
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "b.user_id");

		///
		System.out.println("-------------");
//        int pgno = (int) ((Map<String, String>) map.get("map")).get("pgno");
		System.out.println("currentPage, sizePerPage : " + currentPage + ", " + sizePerPage);
//		String temp = map.get("map");
//		System.out.println(map.get("map").getClass().getTypeName());  // String
		param.put("sido_code", map.get("sido_code"));
		param.put("gugun_code", map.get("gugun_code"));


		if (choiceContent.length() >= 1) {
		//// choiceContent에서 정수값 추출
			String decodedString = URLDecoder.decode(choiceContent, "UTF-8");
			// 쉼표를 기준으로 숫자 값을 추출
			String[] numbers = decodedString.split(",");

			// 숫자 값을 출력
			Arrays.stream(numbers).mapToInt(Integer::parseInt);
			
			for (String temp : numbers) {
				System.out.println("ㅎㅇ : " + temp);
			}
			param.put("choiceContent", numbers);

			///////////////////////////////
		} else {
			param.put("choiceContent", null);
		}
		

		System.out.println("param : " + param.toString());
		System.out.println("choiceContent : " + choiceContent);
//		System.out.println(Arrays.toString(numbers));
//		System.out.println(numbers.length);
		List<AttractionInfoDto> list = attractionMapper.attractionList(param);

		System.out.println("attractionList(param) -> param : " + param);

		System.out.println("attractionList(param)");
		System.out.println(list.toString());

		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
//		int totalAttrCount = attractionMapper.getTotalAttractionCount(param);
		int totalAttrCount = attractionMapper.getTotalAttractionCount(param);  // 6
		int totalPageCount = (totalAttrCount - 1) / sizePerPage + 1;  // 5/ 21 =
		System.out.println(5/21);

		System.out.println("totalAttrCount()");
		System.out.println(totalAttrCount);

		AttractionListDto attractionListDto = new AttractionListDto();
		attractionListDto.setAttractions(list);
		attractionListDto.setCurrentPage(currentPage);
		attractionListDto.setTotalPageCount(totalPageCount);

		return attractionListDto;
	}

	@Override
	public List<AttractionInfoDto> searchByTitle(@Param("title") String title, @Param("sido_code") int sido_code,
			@Param("content_id") int content_id) throws Exception {
		return attractionMapper.searchByTitle(title, sido_code, content_id);
	}

	@Override
	public List<AttractionInfoDto> getPopularList() throws Exception {
		 
		return attractionMapper.getPopularList();
	}

	@Override
	public List<AttractionInfoDto> getStoreList() throws Exception {
		return attractionMapper.getStoreList();
	}
 
}
