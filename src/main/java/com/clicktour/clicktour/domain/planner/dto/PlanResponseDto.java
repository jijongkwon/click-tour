package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Plan;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class PlanResponseDto {
    private Long id;
    private String name;
    private String memo;
    private Date date;
    private Float x;
    private Float y;

    public PlanResponseDto(Plan plan){
        this.id = plan.getId();
        this.name = plan.getName();
        this.memo = plan.getMemo();
        this.x = plan.getX();
        this.y = plan.getY();
        this.date = plan.getDate();
    }
}
