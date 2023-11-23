package com.ssafy.notice.controller;

import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.notice.model.NoticeDto;
import com.ssafy.notice.model.NoticeListDto;
import com.ssafy.notice.model.service.NoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, maxAge = 6000)
@Api(tags = { "Notice 컨트롤러 API" })
@RestController
@RequestMapping("/notice")
public class NoticeController {

	private final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	@Autowired
	private ServletContext servletContext;
	
	private NoticeService noticeService;
	
	public NoticeController(NoticeService noticeService) {
		super();
		this.noticeService = noticeService;
	}
	
	@ApiOperation(value = "공지 글 작성", notes = "공지사항에 글 작성합니다. ")
	@PostMapping
	public ResponseEntity<?> write(
			@RequestBody NoticeDto noticeDto){
		logger.info("writeNotice noticeDto -> {}", noticeDto);
		try {
			System.out.println("WriteNotice noticeDto");
			System.out.println(noticeDto);
			
			if (noticeService.writeNotice(noticeDto) >= 0) {
				System.out.println("NoticeController -> 비속어 필터 걸림!(WRITE)");
				return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
			}else {
				logger.info("write noticeDto : {}", noticeDto);
				return new ResponseEntity<Void>(HttpStatus.CREATED);
			}
		}catch(Exception e) {
			return exceptionHandling(e);
        }
	}
	
	@ApiOperation(value = "공지 글 목록 조회", notes = "공지사항의 <big>전체 목록</big>을 반환해 줍니다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "공지목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping
	public ResponseEntity<?> list(
			@RequestParam @ApiParam(value = "공지사항을 얻기위한 부가정보", required = true) Map<String, String> map){
				logger.info("listNotice map - {}",map);
				try {
					NoticeListDto noticeListDto = noticeService.listNotice(map);
					System.out.println(noticeListDto.toString());
					
					HttpHeaders header = new HttpHeaders();
					header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
					return ResponseEntity.ok().headers(header).body(noticeListDto);
				}catch (Exception e) {
					return exceptionHandling(e);
				}
			}
	
	@ApiOperation(value = "공지 글 상세 조회", notes = "공지사항의 <big>특정 글 상세 조회</big>를 반환해 줍니다.")
	@GetMapping("/{notice_no}")
	public ResponseEntity<NoticeDto> view(
			@PathVariable("notice_no") @ApiParam(value = "얻어올 글의 글번호", required = true) int notice_no) throws Exception{
		logger.info("view notice_no : {}", notice_no);
		logger.info("가져온것", noticeService.getNotice(notice_no));
		noticeService.updateHit(notice_no);
		return new ResponseEntity<NoticeDto>(noticeService.getNotice(notice_no), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "수정 할 글 얻기", notes = "글번호에 해당하는 공지사항의 정보를 반환한다.", response = NoticeDto.class)
	@GetMapping("/modify/{notice_no}")
	public ResponseEntity<NoticeDto> getModifyNotice(
			@PathVariable("notice_no") @ApiParam(value = "얻어올 글의 글번호", required = true) int notice_no) throws Exception{
		logger.info("getModifyNotice - 호출: " + notice_no);
		return new ResponseEntity<NoticeDto>(noticeService.getNotice(notice_no), HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지 글 수정", notes = "공지사항의 특정 게시글의 <big>수정</big>을 합니다.")
	@PutMapping
	public ResponseEntity<String> modifyNotice(
			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) NoticeDto noticeDto) throws Exception{
		logger.info("modifyNotice - 호출: " + noticeDto);
		
		if (noticeService.modifyNotice(noticeDto) >= 0) {
			System.out.println("NoticeController -> 비속어 필터 걸림! (Modify)");
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		}else {
			logger.info("write noticeDto : {}", noticeDto);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
	}

	@ApiOperation(value = "공지 글 삭제", notes = "공지사항의 특정 게시글의 <big>삭제</big>를 합니다.")
	@DeleteMapping("/{notice_no}")
	public ResponseEntity<String> delete(
			@PathVariable("notice_no") @ApiParam(value = "삭제할 글의 글번호", required = true) int notice_no) throws Exception{
		logger.info("delete notice_no: {}", notice_no);
		noticeService.deleteNotice(notice_no);
		return ResponseEntity.ok().build();
	}
	
	private ResponseEntity<?> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
