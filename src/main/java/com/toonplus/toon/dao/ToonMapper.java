package com.toonplus.toon.dao;

import com.toonplus.toon.dto.Toon;

import java.util.List;
//20230920추가
public interface ToonMapper {
	List<Toon> selectToon();

	List<Toon> selectKeywordToon(String keyword);
}