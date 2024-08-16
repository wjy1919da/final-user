package com.cmall.userservice.controller;

import com.cmall.userservice.entity.User;
import com.cmall.userservice.payload.AccountDetailResponse;
import com.cmall.userservice.payload.AccountUpdateDto;
import com.cmall.userservice.service.AccountService;
import com.cmall.userservice.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private AccountService accountService;

    @PutMapping("account")
    public ResponseEntity<User> updateAccountInfo(@RequestBody AccountUpdateDto accountUpdateDto){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        User updatedUser = accountService.updateAccountInfo(user, accountUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/")
    public ResponseEntity<AccountDetailResponse> getUserDetails() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        AccountDetailResponse accountDetails = accountService.getUserDetails(user);
        return ResponseEntity.ok(accountDetails);
    }


}
