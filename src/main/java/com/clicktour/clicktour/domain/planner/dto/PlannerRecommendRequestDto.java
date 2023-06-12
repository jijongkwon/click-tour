package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlannerRecommendRequestDto {
    private Date startDate;
    private Date endDate;
    private String concept;
    private List<Place> placeList;

    public PlannerRecommendRequestDto(Date startDate, Date endDate, String concept, List<Place> placeList) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.concept = concept;
        this.placeList = placeList;
    }
}
