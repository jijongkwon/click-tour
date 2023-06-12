package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Place;
import com.clicktour.clicktour.domain.planner.Plan;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlannerUpdateRequestDto {

    private Long id;
    private String title;
    private String intro;
    private Date start_date;
    private Date end_date;
    private String concept;
    private String visibility;
    private List<Plan> planList;
    private List<Place> placeList;

    @Builder
    public PlannerUpdateRequestDto(Long id, String title, String intro, Date start_date, Date end_date, String concept,
                                   String visibility, List<Plan> planList, List<Place> placeList) {
        this.id = id;
        this.title = title;
        this.intro = intro;
        this.start_date = start_date;
        this.end_date = end_date;
        this.concept = concept;
        this.visibility = visibility;
        this.planList = planList;
        this.placeList = placeList;
    }
}
