package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Planner;
import com.clicktour.clicktour.domain.planner.Plan;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class PlanSaveRequestDto {
    private String name;
    private String memo;
    private Date date;
    private Float x;
    private Float y;
    private Planner planner;

    @Builder
    public PlanSaveRequestDto(Plan plan, Planner planner) {
        this.name = plan.getName();
        this.memo = plan.getMemo();
        this.date = plan.getDate();
        this.x = plan.getX();
        this.y = plan.getY();
        this.planner = planner;
    }

    public Plan toEntity() {
        return Plan.builder().
                name(name).
                date(date).
                x(x).
                y(y).
                memo(memo).
                planner(planner).
                build();
    }
}

