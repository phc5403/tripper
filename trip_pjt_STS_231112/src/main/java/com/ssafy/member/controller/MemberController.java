package com.ssafy.member.controller;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/member")
@CrossOrigin("*")
@Api(tags = { "유저 컨트롤러 API" })
@Slf4j
public class MemberController {

	private final Logger logger = LoggerFactory.getLogger(MemberController.class);

	private MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService) {
		super();
		this.memberService = memberService;
	}

	@GetMapping("/{user_id}")
	@ResponseBody
	public String idCheck(@PathVariable("user_id") String user_id) throws Exception {
		logger.debug("idCheck userid : {}", user_id);
		int cnt = memberService.idCheck(user_id);
		return cnt + "";
	}

	@ApiOperation(value = "회원가입", notes = "회원의 정보를 받아 처리.")
	@PostMapping("/join")
	public ResponseEntity<?> join(MemberDto memberDto, Model model) {
		logger.debug("userDto info : {}", memberDto);
		try {
			memberService.joinUser(memberDto);
			return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return exceptionHandling(e);
		}

	}

	@ApiOperation(value = "로그인", notes = "회원 로그인 기능입니다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "로그인 성공"), @ApiResponse(code = 404, message = "로그인 실패"),
			@ApiResponse(code = 500, message = "서버 오류") })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "user_id", value = "아이디", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "user_pwd", value = "비밀번호", required = true, dataType = "String", paramType = "query"), })
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String user_id, @RequestParam String user_pwd) {
		try {
	        Map<String, String> loginParams = new HashMap<>();
	        loginParams.put("user_id", user_id);
	        loginParams.put("user_pwd", user_pwd);

	        // MyBatis를 사용하여 로그인 쿼리 실행
	        MemberDto memberDto = memberService.loginUser(loginParams);
	        
	        System.out.println("loginParams -> " + ", " + loginParams.toString());
	        System.out.println("userInfo -> " + ", " + user_id + ", " + user_pwd);
	        System.out.println("--------memberDto -> " + ", " + memberDto);

	        if (memberDto != null) {
	            // 로그인 성공일때
	            return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
	        } else {
	            // 로그인 실패일때
	            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
	        }
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}

	@GetMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "회원 로그아웃 기능입니다.")
    public ResponseEntity<String> logout(@ApiIgnore HttpSession session) {
        try {
            // 세션을 무효화하여 사용자를 로그아웃 처리
            session.invalidate();
            return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

//	@ApiOperation(value = "비밀번호 찾기", notes = "회원의 비밀번호를 찾는 기능입니다.")
//    @ApiResponses({
//	    @ApiResponse(code = 200, message = "비밀번호 찾기 성공"),
//	    @ApiResponse(code = 404, message = "비밀번호 찾기 실패"),
//	    @ApiResponse(code = 500, message = "서버 오류")
//	})
//	@ApiImplicitParams({
//	    @ApiImplicitParam(name = "user_name", value = "이름", required = true, dataType = "String", paramType = "query"),
//	    @ApiImplicitParam(name = "user_id", value = "아이디", required = true, dataType = "String", paramType = "query"),
//	})
//    @GetMapping("/findPwd")
//    public ResponseEntity<String> findPassword(@RequestParam String user_name, @RequestParam String user_id) {
//        try {
//            // 이름과 아이디 기반 사용자 정보를 검색
//            MemberDto userDto = memberService.findPassword(user_name, user_id);
//
//            if (userDto != null) {
//                // 비밀번호 보여주기
//                return new ResponseEntity<>("회원님의 비밀번호는 " + userDto + " 입니다.", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("회원 정보를 찾을 수 없습니다.", HttpStatus.NO_CONTENT);
//            }
//        } catch (Exception e) {
//            return exceptionHandling(e);
//        }
//    }

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
