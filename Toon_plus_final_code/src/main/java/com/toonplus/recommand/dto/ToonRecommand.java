package com.toonplus.recommand.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToonRecommand {
    private String User_ID;
    private int Genre_Action;
    private int Genre_Romance;
    private int Genre_Drama;
    private int Genre_Fantasy;
    private int Genre_Comedy;
    private int Genre_Thriller;
    private int Genre_Academy;

}
