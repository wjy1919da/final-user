package com.cmall.userservice.controller;

import com.cmall.userservice.payload.JWTAuthResponse;
import com.cmall.userservice.payload.LoginDto;
import com.cmall.userservice.payload.LoginResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.cmall.userservice.dao.RoleRepository;
import com.cmall.userservice.dao.UserRepository;
import com.cmall.userservice.entity.Role;
import com.cmall.userservice.entity.User;
import com.cmall.userservice.payload.RegisterDto;
import com.cmall.userservice.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthJWTController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil tokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthJWTController.class);

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto){
        logger.info("New User is trying to sign up our application");
        // 检查 email 是否已存在
        if(userRepository.existsByEmail(registerDto.getEmail())){
            return new ResponseEntity<>("Email is already in database!", HttpStatus.BAD_REQUEST);
        }
        // 创建新用户
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsername(registerDto.getUsername());

        // 处理用户角色
        Set<Role> userRoles = new HashSet<>();

        // 获取请求中的 userRole 属性
        Integer userRoleId = registerDto.getUserRole(); // 假设 registerDto 有一个 userRole 字段

        // 根据 userRoleId 设置角色
        if (userRoleId != null) {
            Optional<Role> role = roleRepository.findById(userRoleId);
            if (role.isPresent()) {
                userRoles.add(role.get());
            } else {
                return new ResponseEntity<>("Invalid role ID", HttpStatus.BAD_REQUEST);
            }
        }

        user.setRoles(userRoles);

        // 保存用户
        User response = userRepository.save(user);

        // 生成认证信息
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerDto.getEmail(), registerDto.getPassword())
        );

        // 将认证信息设置到安全上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成JWT token
        String token = tokenProvider.generateToken(authentication);

        // 构建响应
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(token);
        jwtAuthResponse.setUserId(response.getUserId());
        jwtAuthResponse.setUsername(response.getUsername());

        logger.info(registerDto.getEmail() + " registered successfully and token generated");

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        logger.info(loginDto.getEmail() + " is trying to sign in to our application");

        // 首先检查电子邮件是否存在
        Optional<User> userOptional = userRepository.findByEmail(loginDto.getEmail());
        if (!userOptional.isPresent()) {
            logger.error("Login attempt for non-existent email: " + loginDto.getEmail());
            return new ResponseEntity<>("Email does not exist", HttpStatus.BAD_REQUEST);
        }

        // 尝试进行认证
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            // 认证成功后，设置安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 生成JWT token
            String token = tokenProvider.generateToken(authentication);

            // 构建响应
            JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(token);
            logger.info(loginDto.getEmail() + " signed in successfully");

            // 获取用户详细信息并构建登录响应
            User user = userOptional.get(); // 安全地使用get()，因为已检查isPresent()
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserId(user.getUserId()); // 获取并设置用户ID
            loginResponse.setEmail(user.getEmail()); // 获取并设置电子邮件
            loginResponse.setToken(jwtAuthResponse.getAccessToken()); // 设置JWT令牌

            return ResponseEntity.ok(loginResponse);
        } catch (AuthenticationException ex) {
            logger.error("Authentication failed for user: " + loginDto.getEmail(), ex);
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }
    }

}
