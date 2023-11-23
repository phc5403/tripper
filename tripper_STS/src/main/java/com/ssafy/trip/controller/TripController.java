package com.ssafy.trip.controller;

import java.nio.charset.Charset;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.model.AttractionInfoDto;
import com.ssafy.trip.model.AttractionListDto;
import com.ssafy.trip.model.TripDto;
import com.ssafy.trip.model.service.AttractionService;
import com.ssafy.trip.model.service.TripService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@Api(tags = {"Trip 컨트롤러 API"})
@RestController
@RequestMapping("/trip")
public class TripController {
	
	private TripService tripService;
	private AttractionService attractionService;

	@Autowired
	public TripController(TripService tripService, AttractionService attractionService) {
		super();
		this.tripService = tripService;
		this.attractionService = attractionService;
	}
	
	@ApiOperation(value = "시도 목록 받기", notes = "<big>전체 시도 목록</big>을  보여줍니다.")
	@GetMapping("/sido")
	public ResponseEntity<?> sido() {
		try {
//			response.setContentType("application/json;charset=UTF-8");
//			response.setCharacterEncoding("UTF-8");
			List<TripDto> sidoList = tripService.getsSidoList();
			
			ObjectMapper mapper = new ObjectMapper();
//			mapper.writeValue(response.getWriter(), sidoList);
			
			return new ResponseEntity<List<TripDto>>(sidoList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "구군 목록 받기", notes = "시도 코드를 받아서 <big>전체 구군 목록</big>을  보여줍니다.")
	@GetMapping("/gugun")
	public ResponseEntity<?> gugun(int sido_code) {
		try {
//			response.setContentType("application/json;charset=UTF-8");
//			response.setCharacterEncoding("UTF-8");
//			System.out.println("-------------------------");
//			System.out.println(sido_code);
			List<TripDto> gugunList = tripService.getsGugunList(sido_code);
			
//			mapper.writeValue(response.getWriter(), gugunList);
			
			return new ResponseEntity<List<TripDto>>(gugunList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);

		}
	}
	
//	@ApiOperation(value = "지역별 관광지 검색", notes = "<big>지역별 관광지 정보</big>를 보여줍니다.")
//	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "해당 페이지가 없습니다."),
//		@ApiResponse(code = 500, message = "서버에러") })
//	@GetMapping("/list")
//	public ResponseEntity<?> list(@RequestParam int sido_code, @RequestParam int gugun_code) throws Exception {
//		List<AttractionInfoDto> list =  attractionService.attractionList(sido_code, gugun_code);
//		return new ResponseEntity<List<AttractionInfoDto>>(list, HttpStatus.OK);
//	}
	
	@ApiOperation(value = "지역별 관광지 검색", notes = "<big>지역별 관광지 정보</big>를 보여줍니다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "지역별 관광지 정보 OK!!"), @ApiResponse(code = 404, message = "해당 페이지가 없습니다."),
		@ApiResponse(code = 500, message = "서버에러") })
	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam @ApiParam(value = "여행 목록을 얻기위한 부가정보.", required = true) Map<String, String> map, @RequestParam String choiceContent) {
		// GET
		// {pgno:1, spp:20, key:"", word:"" }
		// {sido_code:1, gugun_code:1 pgno:2, spp:20, key:"", word:"" }
		
		try {
			System.out.println("-- Trip Controller --");
//			System.out.println(map.get("sido_code") + ", " + map.get("pgno"));
			System.out.println(map.toString()); 
			System.out.println(choiceContent);
//			System.out.println(map.getClass().getTypeName());
			
//			AttractionListDto attractionListDto = attractionService.attractionList(map);
			AttractionListDto attractionListDto = attractionService.attractionList(map, choiceContent);

			
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(attractionListDto);
			
//			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		
	}
	
	@ApiOperation(value = "원하는 컨텐츠별 관광지 검색", notes = "<big>원하는 컨텐츠별 관광지 정보</big>를 보여줍니다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "관광지 검색 OK!!"), @ApiResponse(code = 404, message = "해당 페이지가 없습니다."),
		@ApiResponse(code = 500, message = "서버에러") })
	@GetMapping("/search")
//	@GetMapping
	public ResponseEntity<?> search(@RequestParam String title, @RequestParam int sido_code, @RequestParam int content_id) throws Exception {
//		attractionService.attractionList(atrractionInfoDto)
		List<AttractionInfoDto> list =  attractionService.searchByTitle(title, sido_code, content_id);
		return new ResponseEntity<List<AttractionInfoDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "인기 있는 관광지 TOP 3 가져오기", notes = "인기 있는 관광지 정보를 보여줍니다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "인기 관광지 목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping("/popular")
	public ResponseEntity<?> popularList(){
		try {
			List<AttractionInfoDto> list = attractionService.getPopularList();
			System.out.println(list.toString());
			
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(list);
		}catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "인기 있는 음식점 TOP 5 가져오기", notes = "인기 있는 음식점 정보를 보여줍니다.")
	@GetMapping("/store")
	public ResponseEntity<?> storeList(){
		try {
			List<AttractionInfoDto> list = attractionService.getStoreList();
			System.out.println(list.toString());
			
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(list);
		}catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	private ResponseEntity<?> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}	
