package com.clicktour.clicktour.common.service;


import org.springframework.stereotype.Service;

@Service
public class CheckService {

    public boolean checkJwtToken(String jwtToken){
        if(jwtToken == null){
            return true;
        }
        return false;
    }
}
