package com.clicktour.clicktour.common.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    NOT_FOUND_PLANNER(404, "notFoundPlanner"),
    DUPLICATE_ID_OR_NICKNAME_OR_EMAIL(400,"duplicateIdOrNicknameOrEmail"),
    NOT_FOUND_ID(404, "not found id"),
    NOT_FOUND_USER(404, "not found user"),
    NOT_FOUND_BOARD(404, "not found board"),
    MISMATCH_PASSWORD(400, "mismatchPassword"),
    FORBIDDEN(403, "Forbidden");

    private final int code;
    private final String message;
}
