package com.toonplus.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToonReview {
    private String TOON_NAME;
    private String USER_ID;
    private String REVIEW_CONTENT;
    private int TOON_EXIST_FOR_REVIEW;
}
