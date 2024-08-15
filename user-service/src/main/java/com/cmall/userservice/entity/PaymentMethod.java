package com.cmall.userservice.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    @Getter
    @Setter
    private int paymentMethodId;

    @Column(name = "user_id", nullable = false)
    @Getter
    @Setter
    private int userId;

    @Column(name = "card_number", nullable = false, length = 16)
    @Getter
    @Setter
    private String cardNumber;

    @Column(name = "expiry_date", nullable = false)
    @Getter
    @Setter
    private Date expiryDate;

    @Column(name = "cvv", nullable = false, length = 3)
    @Getter
    @Setter
    private String cvv;

    @Column(name = "cardholder_name", nullable = false, length = 255)
    @Getter
    @Setter
    private String cardholderName;



}
