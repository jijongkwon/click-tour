package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Planner;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PlannerResponseDto {
    private Long id;
    private String title;
    private String intro;
    private Date start_date;
    private Date end_date;

    private List<PlaceResponseDto> placeList;

    public PlannerResponseDto(Planner planner) {
        this.id = planner.getId();
        this.title = planner.getTitle();
        this.intro = planner.getIntro();
        this.start_date = planner.getStart_date();
        this.end_date = planner.getEnd_date();
        this.placeList = planner.getPlaceList().stream().map(PlaceResponseDto::new).collect(Collectors.toList());
    }
}
