package com.clicktour.clicktour.common.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SuccessMessage {

    SUCCESS_POST(200,"success post");

    private final int code;
    private final String message;
}

