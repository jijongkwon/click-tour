package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Planner;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PlannerDetailResponseDto {

    private String title;
    private String intro;
    private String concept;
    private String nickname;
    private Date start_date;
    private Date end_date;
    private List<PlanResponseDto> planList;
    private List<PlaceResponseDto> placeList;

    public PlannerDetailResponseDto(Planner planner) {
        this.title = planner.getTitle();
        this.intro = planner.getIntro();
        this.start_date = planner.getStart_date();
        this.end_date = planner.getEnd_date();
        this.concept = planner.getConcept();
        this.nickname = planner.getUsers().getNickname();
        this.planList = planner.getPlanList().stream().map(PlanResponseDto::new).collect(Collectors.toList());
        this.placeList = planner.getPlaceList().stream().map(PlaceResponseDto::new).collect(Collectors.toList());
    }
}
