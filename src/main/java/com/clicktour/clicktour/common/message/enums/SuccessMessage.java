package com.clicktour.clicktour.common.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SuccessMessage {

    SUCCESS_POST_PLANNER(200,"success post planner"),
    SUCCESS_POST_BOARD(200,"success post board"),
    SUCCESS_UPDATE_PLANNER(200, "success update planner"),
    SUCCESS_REGISTER(200, "success register"),
    SUCCESS_RECOMMEND(200,"success recommend");

    private final int code;
    private final String message;
}

