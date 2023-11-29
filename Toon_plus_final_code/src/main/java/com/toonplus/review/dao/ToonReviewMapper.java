package com.toonplus.review.dao;

import com.toonplus.review.dto.ToonReview;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//20230920추가
public interface ToonReviewMapper {
	ToonReview selectToonReviewByName(String webtoonName);

	void updateToonReview(@Param("REVIEW_CONTENT") String review_content, @Param("TOON_NAME") String toon_name, @Param("USER_ID") String user_id);
}