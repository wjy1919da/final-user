package com.cmall.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Getter
    @Setter
    @Column(name = "email", nullable = false, length = 255, unique = true)
    @Email(message = "Email should be valid.")
    private String email;

    @Getter
    @Setter
    @Column(name = "username", nullable = false, length = 255)
    @NotBlank(message = "Username cannot be empty.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters long.")
    private String username;

    @Getter
    @Setter
    @Column(name = "password_hash", nullable = false, length = 255)
    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters long.")
    private String passwordHash;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Getter
    @Setter
    private Set<Role> roles;

    @Getter
    @Setter
    @Column(name = "shipping_address")
    private Integer shippingAddress;  // Reference to an Address entity

    @Getter
    @Setter
    @Column(name = "billing_address")
    private Integer billingAddress;  // Reference to an Address entity

    @Getter
    @Setter
    @Column(name = "payment_method_id")
    private Integer paymentMethodId;

}
