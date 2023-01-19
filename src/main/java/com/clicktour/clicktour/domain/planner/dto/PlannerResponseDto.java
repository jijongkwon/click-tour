package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Planner;
import com.clicktour.clicktour.domain.planner.PlannerMap;
import com.clicktour.clicktour.domain.users.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlannerResponseDto {

    private String title;
    private String intro;
    private Date start_date;
    private Date end_date;
    private List<PlannerMap> plannerMapList;

    public PlannerResponseDto(Planner planner){
        this.title = planner.getTitle();
        this.intro = planner.getIntro();
        this.start_date = planner.getStart_date();
        this.end_date = planner.getEnd_date();
        this.plannerMapList = planner.getPlannerMapList();
    }
}
