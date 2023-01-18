package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Planner;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class PlannerSaveRequestDto {

    private String title;
    private String intro;
    private Date start_date;
    private Date end_date;

    @Builder
    public PlannerSaveRequestDto(String title, String intro, Date start_date, Date end_date) {
        this.title = title;
        this.intro = intro;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Planner toEntity() {
        return Planner.builder().
                title(title).
                intro(intro).
                start_date(start_date).
                end_date(end_date).
                build();
    }
}
