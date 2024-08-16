package com.cmall.userservice.payload;

import lombok.Data;

@Data
public class LoginResponse {
    int userId;
    String email;
    String token;
}
