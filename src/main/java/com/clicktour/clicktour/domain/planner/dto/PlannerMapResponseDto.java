package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.PlannerMap;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class PlannerMapResponseDto {
    private String name;
    private String memo;
    private Date date;
    private Float x;
    private Float y;

    public PlannerMapResponseDto(PlannerMap plannerMap){
        this.name = plannerMap.getName();
        this.memo = plannerMap.getMemo();
        this.x = plannerMap.getX();
        this.y = plannerMap.getY();
        this.date = plannerMap.getDate();
    }
}
