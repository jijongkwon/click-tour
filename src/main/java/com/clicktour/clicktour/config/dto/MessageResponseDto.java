package com.clicktour.clicktour.config.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponseDto {

    private int stateCode;
    private String message;

    public MessageResponseDto(int stateCode,String message){
        this.stateCode = stateCode;
        this.message = message;
    }
}
