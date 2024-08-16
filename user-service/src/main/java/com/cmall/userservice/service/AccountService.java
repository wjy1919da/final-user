package com.cmall.userservice.service;

import com.cmall.userservice.entity.User;
import com.cmall.userservice.payload.AccountDetailResponse;
import com.cmall.userservice.payload.AccountUpdateDto;

public interface AccountService {
    public User updateAccountInfo(User user, AccountUpdateDto accountUpdateDto);
    AccountDetailResponse getUserDetails(User user);
}
