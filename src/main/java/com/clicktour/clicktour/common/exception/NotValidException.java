package com.clicktour.clicktour.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class NotValidException extends RuntimeException{
    private final List<String> errorList;
    public NotValidException(List<String> errList) {
        this.errorList = errList;
    }
}
