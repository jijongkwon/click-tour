package com.clicktour.clicktour.common.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    NOT_FOUND_PLANNER(403, "notFoundPlanner"),
    DUPLICATE_ID_OR_NICKNAME_OR_EMAIL(400,"duplicateIdOrNicknameOrEmail"),
    NOT_FOUND_ID(403, "notFoundID"),
    MISMATCH_PASSWORD(400, "mismatchPassword");

    private final int code;
    private final String message;
}
