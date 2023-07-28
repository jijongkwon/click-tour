package com.clicktour.clicktour.common.handler;

import com.clicktour.clicktour.common.dto.NotValidExceptionResponseDto;
import com.clicktour.clicktour.common.exception.NotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice // 사전 컨트롤러
public class CustomizedGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 회원가입
     * @param ex
     * @return code, message, errorList
     */
    @ExceptionHandler(NotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<NotValidExceptionResponseDto> handleCustomizedNotValidException(NotValidException ex) {
        return new ResponseEntity<>(
                NotValidExceptionResponseDto.builder()
                        .stateCode(HttpStatus.BAD_REQUEST.value())
                        .errors(ex.getErrorList())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }
}