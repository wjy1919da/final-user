package com.cmall.userservice.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "email", nullable = false, length = 255, unique = true)
    @Email(message = "Email should be valid.")
    private String email;


    @Column(name = "username", nullable = false, length = 255)
    @NotBlank(message = "Username cannot be empty.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters long.")
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters long.")
    private String passwordHash;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    @Column(name = "shipping_address")
    private Integer shippingAddress;  // Reference to an Address entity


    @Column(name = "billing_address")
    private Integer billingAddress;  // Reference to an Address entity


    @Column(name = "payment_method_id")
    private Integer paymentMethodId;

}
