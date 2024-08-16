package com.cmall.userservice.payload;

import lombok.Getter;
import lombok.Setter;

public class JWTAuthResponse {
    @Getter
    @Setter
    private String accessToken;

    @Getter
    @Setter
    private String tokenType;

    public JWTAuthResponse(String accessToken){
        this.accessToken = accessToken;
    }
}
