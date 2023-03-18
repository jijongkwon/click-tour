package com.clicktour.clicktour.common.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SuccessMessage {

    SUCCESS_POST_PLANNER(200,"success post planner"),
    SUCCESS_POST_BOARD(200,"success post board"),
    SUCCESS_DELETE_PLANNER(200,"success delete planner"),
    SUCCESS_UPDATE_PLANNER(200, "success update planner"),
    SUCCESS_REGISTER(200, "success register"),
    SUCCESS_RECOMMEND(200,"success recommend"),
    SUCCESS_POST_COMMENT(200, "success post comment"),
    SUCCESS_DELETE_BOARD(200,"success delete board"),
    SUCCESS_UPDATE_BOARD(200,"success update board"),
    SUCCESS_DELETE_COMMENT(200,"success delete comment"),
    SUCCESS_UPDATE_COMMENT(200,"success update comment");

    private final int code;
    private final String message;
}

