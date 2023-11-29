package com.toonplus.review.service;

import com.toonplus.review.dao.ToonReviewMapper;
import com.toonplus.review.dto.ToonReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToonReviewServiceImpl {

	private final ToonReviewMapper toonReviewMapper;

	@Autowired
	public ToonReviewServiceImpl(ToonReviewMapper toonReviewMapper) {
		this.toonReviewMapper = toonReviewMapper;
	}

	public ToonReview getToonReviewByName(String webtoonName) {
		return toonReviewMapper.selectToonReviewByName(webtoonName);
	}

	public void updateToonReview(String review_content, String toon_name, String user_id)
	{
		toonReviewMapper.updateToonReview(review_content, toon_name, user_id);
	}



}
