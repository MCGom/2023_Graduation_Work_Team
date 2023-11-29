package com.toonplus.toon.dao;

import com.toonplus.toon.dto.Toon;
import org.apache.ibatis.annotations.Param;

import java.util.List;
//20230920추가
public interface ToonMapper {
	List<Toon> selectToon();
	List<Toon> selectToonByName();
	List<Toon> newToon1();
	List<Toon> newToon2();
	List<Toon> newToon3();

	List<Toon> upToon1();
	List<Toon> upToon2();
	List<Toon> upToon3();
	List<Toon> hotToon1();
	List<Toon> hotToon2();
	List<Toon> hotToon3();
	List<Toon> selectKeywordToon(String keyword);
	//20231013 태그 선택 기능 추가
	List<Toon> tagKeywordToon(String keyword);
	//20231013 이름순 정렬 추가
	List<Toon> tagKeywordToonByName(String keyword);

	List<Toon> toonReview(@Param("USER_ID")String userId);


	Toon selectToonByTitle(String webtoonTitle);

	List<Toon> selectToonByLikes();

	List<Toon> tagKeywordToonByLikes(String keyword);




}