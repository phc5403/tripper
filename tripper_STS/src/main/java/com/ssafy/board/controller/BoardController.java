package com.ssafy.board.controller;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.FileInfoDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, maxAge = 6000)
@Api(tags = { "Board 컨트롤러 API" })
@RestController
@RequestMapping("/board")
public class BoardController {

	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	@Autowired
	private ServletContext servletContext;

	// 파일 업로드  //
	@Value("${file.path}")
	private String uploadPath;
	
	@Value("${file.path.upload-images}")
	private String uploadImagePath;
	
	@Value("${file.path.upload-files}")
	private String uploadFilePath;
	///////////
	
	
	private BoardService boardService;

//	@Autowired
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

	@ApiOperation(value = "게시판 글 작성", notes = "게시판에 <big>글 작성</big>을 합니다.")
  @PostMapping
    public ResponseEntity<?> write(
            @RequestBody BoardDto boardDto) {
        logger.info("writeArticle boardDto -> {}", boardDto);
        try {
            System.out.println("WriteArticle boardDto");
            System.out.println(boardDto);
//            boardService.writeArticle(boardDto); // Default
            
            if(boardService.writeArticle(boardDto) >= 0) {
                System.out.println("BoardController -> 비속어 필터 걸림!(WRITE)");
                return new ResponseEntity<Void>(HttpStatus.ACCEPTED);                
            } else {
                logger.info("write boardDto : {}", boardDto);
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

	@ApiOperation(value = "게시판 글 목록 조회", notes = "게시판의 <big>전체 목록</big>을 반환해 줍니다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
//	@GetMapping("/list")
	@GetMapping
	public ResponseEntity<?> list(
			@RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
//		{pgno:1, spp:20, key:"", word:"" }
		logger.info("listArticle map - {}", map);
		try {
			BoardListDto boardListDto = boardService.listArticle(map);
			System.out.println(boardListDto.toString());
//			System.out.println(boardListDto.getClass().getDeclaredFields().length);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardListDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "게시판 글 상세 조회", notes = "게시판의 <big>특정 글 상세 조회</big>를 반환해 줍니다.")
	@GetMapping("/{board_no}")
//	@GetMapping
	public ResponseEntity<BoardDto> view(
			@PathVariable("board_no") @ApiParam(value = "얻어올 글의 글번호.", required = true) int board_no) throws Exception {
		logger.info("view board_no : {}", board_no);
		logger.info("가져온것", boardService.getArticle(board_no));
		boardService.updateHit(board_no);
		return new ResponseEntity<BoardDto>(boardService.getArticle(board_no), HttpStatus.OK);
	}

	@ApiOperation(value = "수정 할 글 얻기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/modify/{board_no}")
	public ResponseEntity<BoardDto> getModifyArticle(
			@PathVariable("board_no") @ApiParam(value = "얻어올 글의 글번호.", required = true) int board_no) throws Exception {
		logger.info("getModifyArticle - 호출 : " + board_no);
		return new ResponseEntity<BoardDto>(boardService.getArticle(board_no), HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글 수정", notes = "게시판의 특정 게시글의 <big>수정</big>을 합니다.")
//	@PutMapping("/modify/{board_no}")
	@PutMapping
	public ResponseEntity<String> modifyArticle(
			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardDto boardDto) throws Exception {
		logger.info("modifyARticle - 호출 {}", boardDto);
//		boardService.modifyArticle(boardDto);

//        return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
		
		
		if(boardService.modifyArticle(boardDto) >= 0) {
			System.out.println("BoardController -> 비속어 필터 걸림!(MODIFY)");
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);				
		} else {
			logger.info("write boardDto : {}", boardDto);
			return new ResponseEntity<String>(HttpStatus.CREATED);
//			return ResponseEntity.ok().build();
		}
	}

	@ApiOperation(value = "게시판 글 삭제", notes = "게시판의 특정 게시글의 <big>삭제</big>를 합니다.")
	@DeleteMapping("/{board_no}")
//	@DeleteMapping
	public ResponseEntity<String> delete(
			@PathVariable("board_no") @ApiParam(value = "살제할 글의 글번호.", required = true) int board_no) throws Exception {
		logger.info("delete board_no: {}", board_no);
		boardService.deleteArticle(board_no);
		return ResponseEntity.ok().build();
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}