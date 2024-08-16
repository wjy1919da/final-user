package com.cmall.userservice.service.impl;
import com.cmall.userservice.dao.AddressRepository;
import com.cmall.userservice.dao.PaymentMethodRepository;
import com.cmall.userservice.dao.UserRepository;
import com.cmall.userservice.entity.Address;
import com.cmall.userservice.entity.PaymentMethod;
import com.cmall.userservice.entity.User;
import com.cmall.userservice.payload.AccountDetailResponse;
import com.cmall.userservice.payload.AccountUpdateDto;
import com.cmall.userservice.payload.AddressDto;
import com.cmall.userservice.payload.PaymentMethodDto;
import com.cmall.userservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public User updateAccountInfo(User user, AccountUpdateDto accountUpdateDto){
        // 更新或添加新的送货地址
        accountUpdateDto.getShippingAddress().ifPresent(address -> {
            // 检查是否已存在地址ID，如果不存在则认为是新地址
            if (address.getAddressId() == 0) {
                address.setUserId(user.getUserId());
                Address savedAddress = addressRepository.save(address);
                user.setShippingAddress(savedAddress.getAddressId());
            }
        });

        // 更新或添加新的账单地址
        accountUpdateDto.getBillingAddress().ifPresent(address -> {
            // 检查是否已存在地址ID，如果不存在则认为是新地址
            if (address.getAddressId() == 0) {
                address.setUserId(user.getUserId());
                Address savedAddress = addressRepository.save(address);
                user.setBillingAddress(savedAddress.getAddressId());
            }
        });

        // 更新或添加新的支付方式
        accountUpdateDto.getPaymentMethod().ifPresent(paymentMethod -> {
            // 检查是否已存在支付方法ID，如果不存在则认为是新支付方法
            if (paymentMethod.getPaymentMethodId() == 0) {
                paymentMethod.setUserId(user.getUserId());
                PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);
                user.setPaymentMethodId(savedPaymentMethod.getPaymentMethodId());
            }
        });

        userRepository.save(user);
        return user;
    }

    @Override
    public AccountDetailResponse getUserDetails(User user){
        AccountDetailResponse response = new AccountDetailResponse();
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setShippingAddress(convertAddress(addressRepository.findById(user.getShippingAddress()).orElse(null)));
        response.setBillingAddress(convertAddress(addressRepository.findById(user.getBillingAddress()).orElse(null)));
        response.setPaymentMethod(convertPaymentMethod(paymentMethodRepository.findById(user.getPaymentMethodId()).orElse(null)));

        return response;
    }

    private AddressDto convertAddress(Address address) {
        if (address == null) return null;
        AddressDto dto = new AddressDto();
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        return dto;
    }

    private PaymentMethodDto convertPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) return null;
        PaymentMethodDto dto = new PaymentMethodDto();
        dto.setCardNumber(paymentMethod.getCardNumber());
        dto.setExpiryDate(paymentMethod.getExpiryDate());
        dto.setCvv(paymentMethod.getCvv());
        dto.setCardholderName(paymentMethod.getCardholderName());
        return dto;
    }

}
