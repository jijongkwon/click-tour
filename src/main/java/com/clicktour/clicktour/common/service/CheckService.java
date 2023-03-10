package com.clicktour.clicktour.common.service;


import org.springframework.stereotype.Service;

@Service
public class CheckService {

    public boolean checkJwtToken(String jwtToken){
        return jwtToken == null || jwtToken.equals("");
    }
}
