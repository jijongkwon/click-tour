package com.clicktour.clicktour.common.dto;

import com.clicktour.clicktour.common.enums.SuccessMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto {

    private int stateCode;
    private String message;

    public ResponseDto(SuccessMessage successMessage) {
        this.stateCode = successMessage.getCode();
        this.message = successMessage.getMessage();
    }
}
