package com.ssafy.user.controller;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.service.UserService;
import com.ssafy.util.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/user")
@Api(tags = { "유저 컨트롤러 API" })
@Slf4j
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private JWTUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JWTUtil jwtUtil) {
        super();
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    
    @ApiOperation(value = "유저 정보 가져오기", notes = "유저 아이디에 해당하는 정보를 반환합니다.", response = UserDto.class)
    @GetMapping("/modify/{user_id}")
    public ResponseEntity<UserDto> getModifyUser(
            @PathVariable("user_id") @ApiParam(value = "얻어올 유저의 아이디", required = true) String user_id) throws Exception{
        logger.info("getUser - 호출: " + user_id);
        return new ResponseEntity<UserDto>(userService.getUser(user_id), HttpStatus.OK);
    }
    
    @ApiOperation(value = "유저 정보 수정", notes = "유저 정보 수정합니다.")
    @PutMapping
    public ResponseEntity<String> modifyUser(
            @RequestBody @ApiParam(value = "수정할 유저 정보", required = true) UserDto userDto) throws Exception{
        logger.info("updateUser - 호출 {}", userDto);
        userService.updateUser(userDto);
        
        return ResponseEntity.ok().build();
    }
    
    @ApiOperation(value = "유저 정보 삭제", notes = "유저 정보를 삭제합니다.")
    @DeleteMapping("/{user_id}")
    public ResponseEntity<String> delete(@PathVariable("user_id") @ApiParam(value = "삭제할 유저의 아이디", required = true) String user_id) throws Exception{
    	logger.info("delete user_id: {}", user_id);
    	userService.deleteUser(user_id);
    	return ResponseEntity.ok().build();
    }
    
    
    @GetMapping("/{user_id}")
    @ResponseBody
    public String idCheck(@PathVariable("user_id") String user_id) throws Exception {
        logger.debug("idCheck userid : {}", user_id);
        int cnt = userService.idCheck(user_id);
        return cnt + "";
    }

    @ApiOperation(value = "회원가입", notes = "회원의 정보를 받아 처리.")
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @ApiParam(value="회원가입한 유저의 정보", required = true) UserDto userDto) {
        logger.debug("userDto info : {}", userDto);
        try {
            userService.joinUser(userDto);
            return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return exceptionHandling(e);
        }

    }

    @ApiOperation(value = "로그인", notes = "회원 로그인 기능입니다.")
    @ApiResponses({ @ApiResponse(code = 200, message = "로그인 성공"), @ApiResponse(code = 404, message = "로그인 실패"),
            @ApiResponse(code = 500, message = "서버 오류") })
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "user_id", value = "아이디", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "user_pwd", value = "비밀번호", required = true, dataType = "String", paramType = "query"), })
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @ApiParam(value="로그인 시 필요한 회원정보(아이디, 비밀번호).", required=true) UserDto userDto) {
        logger.debug("login user: ", userDto);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            UserDto loginUser = userService.loginUser(userDto);

            // MyBatis를 사용하여 로그인 쿼리 실행
//            UserDto userDto = userService.loginUser(loginParams);
            
//            System.out.println("loginParams -> " + ", " + loginParams.toString());
//            System.out.println("userInfo -> " + ", " + user_id + ", " + user_pwd);
//            System.out.println("--------userDto -> " + ", " + userDto);

            if (loginUser != null) {
                // 로그인 성공일때
                String accessToken = jwtUtil.createAccessToken(loginUser.getUser_id());
                String refreshToken = jwtUtil.createRefreshToken(loginUser.getUser_id());
                logger.debug("access token : {}", accessToken);
                logger.debug("refresh token : {}", refreshToken);
                
                //발급받은 refresh token을 DB에 저장.
                userService.saveRefreshToken(loginUser.getUser_id(), refreshToken);
                //JSON으로 token 전달.
                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);

                status = HttpStatus.CREATED;
                
//                return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
            } else {
                // 로그인 실패일때
                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
                status = HttpStatus.UNAUTHORIZED;
//                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            logger.debug("로그인 에러 발생 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
//            return exceptionHandling(e);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    
    @ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @GetMapping("/info/{userId}")
    public ResponseEntity<Map<String,Object>> getInfo(@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String user_id,
            HttpServletRequest request){
        
        //log.debug("user_id: {}",user_id);
        Map<String, Object> resultMap = new  HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;;
        // token의 유효성 검증
        if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                //로그인 사용자 정보
                UserDto userDto = userService.userInfo(user_id);
                resultMap.put("userInfo", userDto);
                status = HttpStatus.OK;
            }catch(Exception e) {
                logger.error("정보조회 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }else {
            logger.error("사용 불가능 토큰");
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String,Object>>(resultMap,status);
        
    }
    
    //refresh token 삭제
        @ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
        @GetMapping("/logout/{userId}")
        public ResponseEntity<?> removeToken(@PathVariable ("userId") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String user_id){
            Map<String, Object> resultMap = new HashMap<>();
            HttpStatus status = HttpStatus.ACCEPTED;
            try {
                //삭제 요청
                userService.deleRefreshToken(user_id);
                status = HttpStatus.OK;
            }catch(Exception e) {
                logger.error("로그아웃 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }

//    @GetMapping("/logout")
//    @ApiOperation(value = "로그아웃", notes = "회원 로그아웃 기능입니다.")
//    public ResponseEntity<String> logout(@ApiIgnore HttpSession session) {
//        try {
//            // 세션을 무효화하여 사용자를 로그아웃 처리
//            session.invalidate();
//            return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);
//        } catch (Exception e) {
//            return exceptionHandling(e);
//        }
//    }
        
        //acess token 재발급
        @ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
        @PostMapping("/refresh")
        public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception{
            Map<String, Object> resultMap = new HashMap<>();
            HttpStatus status = HttpStatus.ACCEPTED;
            //header의 refresh token 받고
            String token = request.getHeader("refreshToken");
            logger.debug("token : {}, userDto : {}", token, userDto);
            //DB에 저장되어 있는 refresh token과 비교 후 재발급
            if (jwtUtil.checkToken(token)) {
                if (token.equals(userService.getRefreshToken(userDto.getUser_id()))) {
                    String accessToken = jwtUtil.createAccessToken(userDto.getUser_id());
                    logger.debug("token : {}", accessToken);
                    logger.debug("정상적으로 액세스토큰 재발급!!!");
                    resultMap.put("access-token", accessToken);
                    status = HttpStatus.CREATED;
                }
            }else {
                logger.debug("리프레쉬토큰도 사용불가!!");
                status = HttpStatus.UNAUTHORIZED;
            }
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }

    @ApiOperation(value = "비밀번호 찾기", notes = "회원의 비밀번호를 찾는 기능입니다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "비밀번호 찾기 성공"),
        @ApiResponse(code = 404, message = "비밀번호 찾기 실패"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "user_name", value = "이름", required = true, dataType = "String", paramType = "query"),
//        @ApiImplicitParam(name = "user_id", value = "아이디", required = true, dataType = "String", paramType = "query"),
//    })
    @GetMapping("/findPwd")
    public ResponseEntity<String> findPassword(@RequestParam String user_name, @RequestParam String user_id) {
        try {
            // 이름과 아이디 기반 사용자 정보를 검색
//            UserDto userDto = userService.findPassword(user_name, user_id);
            String temp = userService.findPassword(user_name, user_id);

            if (temp != null) {
                // 비밀번호 보여주기
                return new ResponseEntity<>("회원님의 비밀번호는 " + temp + " 입니다.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("회원 정보를 찾을 수 없습니다.", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}