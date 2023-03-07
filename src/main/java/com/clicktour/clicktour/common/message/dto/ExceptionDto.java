package com.clicktour.clicktour.common.message.dto;

import com.clicktour.clicktour.common.message.ErrorMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionDto {

    private int stateCode;
    private String message;

    public ExceptionDto(ErrorMessage errorMessage){
        this.stateCode = errorMessage.getCode();
        this.message = errorMessage.getMessage();
    }
}
