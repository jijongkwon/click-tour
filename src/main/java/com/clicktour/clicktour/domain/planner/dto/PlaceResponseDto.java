package com.clicktour.clicktour.domain.planner.dto;

import com.clicktour.clicktour.domain.planner.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceResponseDto {
    private String place;

    public PlaceResponseDto(Place place) {
        this.place = place.getPlace();
    }
}
