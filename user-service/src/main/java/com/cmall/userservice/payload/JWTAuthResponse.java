package com.cmall.userservice.payload;

import com.cmall.userservice.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class JWTAuthResponse {
    private String accessToken;
    private int userId;
    private String username;

    public JWTAuthResponse(String accessToken){
        this.accessToken = accessToken;
    }
}
