package com.cmall.userservice.payload;

import lombok.Getter;
import lombok.Setter;

public class AddressDto {
    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String state;

    @Getter
    @Setter
    private String postalCode;

    @Getter
    @Setter
    private String country;
}
