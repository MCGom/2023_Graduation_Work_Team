package com.toonplus.toon.service;

import com.toonplus.toon.dao.ToonMapper;
import com.toonplus.toon.dto.Toon;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//20230920추가
@Service
public class ToonServiceImpl {

	private final ToonMapper toonMapper;

	@Autowired
	public ToonServiceImpl(ToonMapper toonMapper) {
		this.toonMapper = toonMapper;
	}
	// mysql 연동 20230920추가
	// "" 큰따옴표 제거 로직
	public List<Toon> getAllToons() {
		List<Toon> toons = toonMapper.selectToon();
		for (Toon toon : toons) {
			toon.setTOON_IMAGELINK(toon.getTOON_IMAGELINK().replace("\"", ""));
			toon.setTOON_LINK(toon.getTOON_LINK().replace("\"", ""));
			toon.setTOON_PLATFORM(toon.getTOON_PLATFORM());
			toon.setTOON_NAME(toon.getTOON_NAME());
			toon.setTOON_LIKES(toon.getTOON_LIKES());
		}
		return toons;
	}

	// 20231013 추가 - 랜덤 전체 조회
	public List<Toon> searchToons() {
		List<Toon> toons = toonMapper.selectToon();
		return toons;
	}
	// 20231013 추가 - 이름순 전체 조회
	public List<Toon> searchToonsByName() {
		List<Toon> toons = toonMapper.selectToonByName();
		return toons;
	}

	public List<Toon> searchToonsByKeyword(String keyword) {
		List<Toon> toons = toonMapper.selectKeywordToon(keyword);
		return toons;
	}

	// 20231013 추가 - 태그에 맞는 랜덤 조회
	public List<Toon> tagToonsByKeyword(String keyword) {
		List<Toon> toons = toonMapper.tagKeywordToon(keyword);
		return toons;
	}
	// 20231013 추가 - 태그에 맞는 이름순 조회
	public List<Toon> tagToonsByKeywordByName(String keyword) {
		List<Toon> toons = toonMapper.tagKeywordToonByName(keyword);
		return toons;
	}

}
