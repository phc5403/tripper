package com.ssafy.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
@Api(tags = {"Admin 컨트롤러 API"})
@Slf4j
public class AdminUserController {
	
	private MemberService memberService;
	
	@Autowired
	public AdminUserController(MemberService memberService) {
		 this.memberService = memberService;
	}
	
	@ApiOperation(value = "회원목록", notes = "회원의 <big>전체목록</big>을 반환해 줍니다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "해당 페이지가 없습니다."),
			@ApiResponse(code = 500, message = "서버에러") })
	@GetMapping(value = "/user")
	public ResponseEntity<?> userList(){
//		log.debug("userList call");
		try {
			List<MemberDto> list = memberService.listUser(null);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
//				return new ResponseEntity<List<MemberDto>>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
			
	}
	
	@ApiOperation(value = "회원등록", notes = "회원의 정보를 받아 처리.")
	@PostMapping(value = "/user")
	public ResponseEntity<?> userRegister(@RequestBody MemberDto memberDto){
//		log.debug("userRegister userDto : {}", userDto);
		try {
			memberService.joinUser(memberDto);
			List<MemberDto> list = memberService.listUser(null);
			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
//			return new ResponseEntity<Integer>(cnt, HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "회원정보", notes = "회원 한 명에 대한 정보.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "아이디", required = true, dataType = "String", paramType = "path")
	})
	@GetMapping(value = "/user/{user_id}")
	public ResponseEntity<?> userInfo(@PathVariable("user_id") String user_id){
//		log.debug("userInfo userid : {}", userId);
		try {
			MemberDto memberDto = memberService.getUser(user_id);
			if(memberDto != null)
				return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "회원정보수정", notes = "회원정보를 수정합니다.")
	@PutMapping(value = "/user")
	public ResponseEntity<?> userModify(@RequestBody MemberDto userDto){
//		log.debug("userModify userDto : {}", userDto);
		try {
			memberService.updateUser(userDto);
			List<MemberDto> list = memberService.listUser(null);
			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "회원정보삭제", notes = "회원정보를 삭제합니다.")
	@DeleteMapping(value = "/user/{user_id}")
	public ResponseEntity<?> userDelete(@PathVariable("user_id") String user_id){
		try {
			memberService.deleteUser(user_id);
			List<MemberDto> list = memberService.listUser(null);
			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<?> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}