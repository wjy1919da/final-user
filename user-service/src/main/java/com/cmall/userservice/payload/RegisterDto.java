package com.cmall.userservice.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class RegisterDto {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Getter
    @Setter
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Getter
    @Setter
    private String password;

    @NotBlank(message = "User role cannot be blank")
    @Getter
    @Setter
    private String userRole;
}
