package com.clicktour.clicktour.common.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SuccessMessage {

    SUCCESS_POST(200,"success post"),
    SUCCESS_UPDATE(200, "success update");

    private final int code;
    private final String message;
}

