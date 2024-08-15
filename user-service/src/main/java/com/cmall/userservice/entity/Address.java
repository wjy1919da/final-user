package com.cmall.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    @Getter
    @Setter
    private int addressId;

    @Column(name = "user_id", nullable = false)
    @Getter
    @Setter
    private int userId;

    @Column(name = "type", nullable = false, length = 50)
    @Getter
    @Setter
    private String type;


    @Column(name = "city", nullable = false, length = 100)
    @Getter
    @Setter
    private String city;

    @Column(name = "state", nullable = false, length = 100)
    @Getter
    @Setter
    private String state;

    @Column(name = "postal_code", nullable = false, length = 20)
    @Getter
    @Setter
    private String postalCode;

    @Column(name = "country", nullable = false, length = 100)
    @Getter
    @Setter
    private String country;
}
