package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Planner;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class PlannerResponseDto {
    private String title;
    private String intro;
    private Date start_date;
    private Date end_date;

    public PlannerResponseDto(Planner planner) {
        this.title = planner.getTitle();
        this.intro = planner.getIntro();
        this.start_date = planner.getStart_date();
        this.end_date = planner.getEnd_date();
    }
}
