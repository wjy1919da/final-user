package com.cmall.userservice.utils;

import com.cmall.userservice.dao.RoleRepository;
import com.cmall.userservice.dao.UserRepository;
import com.cmall.userservice.entity.Role;
import com.cmall.userservice.entity.User;
import com.cmall.userservice.payload.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));

        return new CustomUserDetails(user);
    }

//    public User registerNewUserAccount(RegisterDto registrationDto) {
//        if (userRepository.existsByEmail(registrationDto.getEmail())) {
//            throw new RuntimeException("Email already exists.");
//        }
//
//        User newUser = new User();
//        newUser.setEmail(registrationDto.getEmail());
//        newUser.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
//        newUser.setRoles(registrationDto.getUserRole());
//
//        // Default role as Customer
//        Role customerRole = roleRepository.findById(2)  // Assuming '2' is the identifier for Customer
//                .orElseThrow(() -> new RuntimeException("Role not found"));
//
//        newUser.setRoles(Collections.singleton(customerRole));
//        return userRepository.save(newUser);
//    }

}
