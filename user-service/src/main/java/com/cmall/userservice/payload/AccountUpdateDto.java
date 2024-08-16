package com.cmall.userservice.payload;

import com.cmall.userservice.entity.Address;
import com.cmall.userservice.entity.PaymentMethod;
import lombok.Data;

import java.util.Optional;

@Data
public class AccountUpdateDto {
    private Optional<Address> shippingAddress = Optional.empty();
    private Optional<Address> billingAddress =  Optional.empty();
    private Optional<PaymentMethod> paymentMethod = Optional.empty();
}
