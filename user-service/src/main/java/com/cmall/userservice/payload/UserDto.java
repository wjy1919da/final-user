package com.cmall.userservice.payload;

import lombok.Getter;
import lombok.Setter;

public class UserDto {
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private AddressDto shippingAddress;

    @Getter
    @Setter
    private AddressDto paymentAddress;

}
