package com.clicktour.clicktour.common.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class NotValidExceptionResponseDto extends ExceptionDto{
    private final List<String> errors;
}
