package com.toonplus.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewObject {
    private String USER_ID;
    private String REVIEW_CONTENT;
    private String USER_NICKNAME;

}
