package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Place;
import com.clicktour.clicktour.domain.planner.Planner;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceSaveRequestDto {
    private String place;
    private Planner planner;

    @Builder
    public PlaceSaveRequestDto(Place place, Planner planner){
        this.place = place.getPlace();
        this.planner = planner;
    }

    public Place toEntity(){
        return Place.builder().
                place(place).
                planner(planner).
                build();
    }

}
