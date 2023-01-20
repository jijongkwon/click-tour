package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Planner;
import com.clicktour.clicktour.domain.planner.PlannerMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class PlannerMapSaveRequestDto {
    private String name;
    private String memo;
    private Date date;
    private Float x;
    private Float y;
    private Planner planner;

    @Builder
    public PlannerMapSaveRequestDto(PlannerMap plannerMap, Planner planner) {
        this.name = plannerMap.getName();
        this.memo = plannerMap.getMemo();
        this.date = plannerMap.getDate();
        this.x = plannerMap.getX();
        this.y = plannerMap.getY();
        this.planner = planner;
    }

    public PlannerMap toEntity() {
        return PlannerMap.builder().
                name(name).
                date(date).
                x(x).
                y(y).
                memo(memo).
                planner(planner).
                build();
    }
}

