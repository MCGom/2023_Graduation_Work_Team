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
		}
		return toons;
	}
	public List<Toon> searchToonsByKeyword(String keyword) {
		List<Toon> toons = toonMapper.selectKeywordToon(keyword);
		return toons;
	}
}
