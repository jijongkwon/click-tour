package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Planner;
import com.clicktour.clicktour.domain.planner.Plan;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlannerSaveRequestDto {

    private String title;
    private String intro;
    private Date start_date;
    private Date end_date;
    private List<Plan> planList;

    @Builder
    public PlannerSaveRequestDto(String title, String intro, Date start_date, Date end_date,
                                 List<Plan> planList) {
        this.title = title;
        this.intro = intro;
        this.start_date = start_date;
        this.end_date = end_date;
        this.planList = planList;
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
