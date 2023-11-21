package com.ssafy.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.badword.model.BadWordDto;
import com.ssafy.badword.service.BadWordService;
import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.FileInfoDto;
import com.ssafy.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	BoardMapper mapper;
	BadWordService badWordService;

	@Autowired
	public BoardServiceImpl(BoardMapper mapper, BadWordService badWordService) {
		super();
		this.mapper = mapper;
		this.badWordService = badWordService;
	}

	@Override
	public int writeArticle(BoardDto boardDto) throws Exception {
		// 비속어 검사
		String title = boardDto.getBoard_title();
		String content = boardDto.getBoard_content();

		System.out.println("-- BoardServiceImpl - writeArticle --");

		List<BadWordDto> badList = badWordService.getBadList();
		System.out.println(badList.toString());

		String pattern = "";
		for (int i = 0; i < badList.size(); i++) {
			pattern = badList.get(i).getFword();

			int titleChk = badWordService.boyerMoore(title, pattern);
			int contChk = badWordService.boyerMoore(content, pattern);
//		 		System.out.println("1 : " + titleChk + ", " + contChk);

			// 0 이상이면 걸림
			if (titleChk >= 0 || contChk >= 0) {
//					System.out.println("검거");
				System.out.println("비속어 필터 걸림");
				return 0;
//		 			return;
			}
		}

		// 글 작성
		mapper.writeArticle(boardDto);
		List<FileInfoDto> fileInfos = boardDto.getFileInfos();
		System.out.println(fileInfos);
		
		if (fileInfos != null && !fileInfos.isEmpty()) {
			mapper.registerFile(boardDto);
		}
		return -1;
	}

	@Override
	public BoardListDto listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
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
		List<BoardDto> list = mapper.listArticle(param);

		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
		int totalArticleCount = mapper.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setArticles(list);
		boardListDto.setCurrentPage(currentPage);
		boardListDto.setTotalPageCount(totalPageCount);

		return boardListDto;
	}

	@Override
	public BoardDto getArticle(int board_no) throws Exception {
		return mapper.getArticle(board_no);
	}

	@Override
	public void updateHit(int board_no) throws Exception {
		mapper.updateHit(board_no);
	}

	@Override
	public int modifyArticle(BoardDto boardDto) throws Exception {
		// 비속어 검사
		String title = boardDto.getBoard_title();
		String content = boardDto.getBoard_content();

		System.out.println("-- BoardServiceImpl - writeArticle --");

		List<BadWordDto> badList = badWordService.getBadList();
		System.out.println(badList.toString());

		String pattern = "";
		for (int i = 0; i < badList.size(); i++) {
			pattern = badList.get(i).getFword();

			int titleChk = badWordService.boyerMoore(title, pattern);
			int contChk = badWordService.boyerMoore(content, pattern);
//				 		System.out.println("1 : " + titleChk + ", " + contChk);

			// 0 이상이면 걸림
			if (titleChk >= 0 || contChk >= 0) {
//							System.out.println("검거");
				System.out.println("비속어 필터 걸림");
				return 0;
//				 			return;
			}
		}

		// 글 작성
		mapper.modifyArticle(boardDto);
		List<FileInfoDto> fileInfos = boardDto.getFileInfos();
		if (fileInfos != null && !fileInfos.isEmpty()) {
			mapper.registerFile(boardDto);
		}
		return -1;
	}

	@Override
	public void deleteArticle(int board_no) throws Exception {
		mapper.deleteArticle(board_no);
	}

}
