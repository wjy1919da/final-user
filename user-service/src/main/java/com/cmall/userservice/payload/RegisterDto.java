package com.cmall.userservice.payload;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterDto {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Getter
    @Setter
    private String email;

    @NotBlank(message = "Username cannot be empty.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters long.")
    @Getter
    @Setter
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Getter
    @Setter
    private String password;

    @NotBlank(message = "User role cannot be blank")
    @Getter
    @Setter
    private int userRole;
}
