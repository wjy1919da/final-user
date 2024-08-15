package com.cmall.userservice.utils;

import com.cmall.userservice.dao.RoleRepository;
import com.cmall.userservice.dao.UserRepository;
import com.cmall.userservice.entity.Role;
import com.cmall.userservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));

        return new CustomUserDetails(user);
    }

//    public User registerNewUserAccount(UserRegistrationDto registrationDto) {
//        if (userRepository.existsByEmail(registrationDto.getEmail())) {
//            throw new RuntimeException("Email already exists.");
//        }
//
//        User newUser = new User();
//        newUser.setEmail(registrationDto.getEmail());
//        newUser.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
//
//        // Default role as Customer
//        Role customerRole = roleRepository.findById(2)  // Assuming '2' is the identifier for Customer
//                .orElseThrow(() -> new RuntimeException("Role not found"));
//
//        newUser.setRoles(Collections.singleton(customerRole));
//        return userRepository.save(newUser);
//    }


}
