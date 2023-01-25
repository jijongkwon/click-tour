package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.PlannerMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlannerUpdateRequestDto {

    private String title;
    private String intro;
    private Date start_date;
    private Date end_date;
    private List<PlannerMap> plannerMapList;

    @Builder
    public PlannerUpdateRequestDto(String title, String intro, Date start_date, Date end_date,
                                 List<PlannerMap> plannerMapList) {
        this.title = title;
        this.intro = intro;
        this.start_date = start_date;
        this.end_date = end_date;
        this.plannerMapList = plannerMapList;
    }
}
