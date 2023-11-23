package com.ssafy.notice.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.badword.model.BadWordDto;
import com.ssafy.badword.service.BadWordService;
import com.ssafy.notice.model.NoticeDto;
import com.ssafy.notice.model.NoticeListDto;
import com.ssafy.notice.model.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {
	NoticeMapper mapper;
	BadWordService badWordService;
	
	@Autowired
	public NoticeServiceImpl(NoticeMapper mapper, BadWordService badWordService) {
		super();
		this.mapper = mapper;
		this.badWordService = badWordService;
	}
	
	@Override
	@Transactional
	public int writeNotice(NoticeDto noticeDto) throws Exception {
		String title = noticeDto.getNotice_title();
		String content = noticeDto.getNotice_content();
		
		System.out.println("-- NoticeServiceImpl - writeNotice --");
		
		List<BadWordDto> badList = badWordService.getBadList();
		System.out.println(badList.toString());
		
		String pattern = "";
		for (int i = 0; i < badList.size(); i++) {
			pattern = badList.get(i).getFword();
			
			int titleChk = badWordService.boyerMoore(title, pattern);
			int contChk = badWordService.boyerMoore(content, pattern);
			
			// 0 이상이면 걸림
						if (titleChk >= 0 || contChk >= 0) {
//								System.out.println("검거");
							System.out.println("비속어 필터 걸림");
							return 0;
//					 			return;
						}
		}
		
		// 글 작성
		mapper.writeNotice(noticeDto);
		return -1;
		
	}

	@Override
	public NoticeListDto listNotice(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : (String) map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : (String) map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);
		
		System.out.println("NoticeServiceImpl : " + param.toString());
		
		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "n.user_id");
		List<NoticeDto> list = mapper.listNotice(param);
		
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
		int totalArticleCount = mapper.getTotalNoticeCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
		
		NoticeListDto noticeListDto = new NoticeListDto();
		noticeListDto.setNotices(list);
		noticeListDto.setCurrentPage(currentPage);
		noticeListDto.setTotalPageCount(totalPageCount);
		
		return noticeListDto;
		
	}

	@Override
	public NoticeDto getNotice(int notice_no) throws Exception {
		return mapper.getNotice(notice_no);
	}

	@Override
	public void updateHit(int notice_no) throws Exception {
		mapper.updateHit(notice_no);

	}

	@Override
	public int modifyNotice(NoticeDto noticeDto) throws Exception {
		// 비속어 검사
		String title = noticeDto.getNotice_title();
		String content = noticeDto.getNotice_content();
		
		System.out.println("-- NoticeServiceImpl - writeNotice --");
		
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
		
		mapper.modifyNotice(noticeDto);
		return -1;
	}

	@Override
	public void deleteNotice(int notice_no) throws Exception {
		mapper.deleteNotice(notice_no);
	}

}
