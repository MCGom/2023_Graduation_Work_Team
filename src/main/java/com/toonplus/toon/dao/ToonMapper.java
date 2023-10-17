package com.toonplus.toon.dao;

import com.toonplus.toon.dto.Toon;

import java.util.List;
//20230920추가
public interface ToonMapper {
	List<Toon> selectToon();
	List<Toon> selectToonByName();

	List<Toon> selectKeywordToon(String keyword);
	//20231013 태그 선택 기능 추가
	List<Toon> tagKeywordToon(String keyword);
	//20231013 이름순 정렬 추가
	List<Toon> tagKeywordToonByName(String keyword);
}