package com.clicktour.clicktour.common.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    NOT_FOUND_PLANNER(403, "notFoundPlanner");

    private final int code;
    private final String message;
}
