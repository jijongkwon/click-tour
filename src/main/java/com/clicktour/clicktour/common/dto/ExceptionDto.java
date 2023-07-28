package com.clicktour.clicktour.common.dto;

import com.clicktour.clicktour.common.enums.ErrorMessage;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class ExceptionDto {

    private int stateCode;

    public ExceptionDto(ErrorMessage errorMessage) {
        this.stateCode = errorMessage.getCode();
    }
}
