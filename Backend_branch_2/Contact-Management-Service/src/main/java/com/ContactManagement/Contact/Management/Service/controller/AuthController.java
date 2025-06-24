package com.ContactManagement.Contact.Management.Service.controller;

import com.ContactManagement.Contact.Management.Service.dto.request.UserRequestDTO;
import com.ContactManagement.Contact.Management.Service.model.User;
import com.ContactManagement.Contact.Management.Service.repository.UserRepository;
import com.ContactManagement.Contact.Management.Service.securityConfig.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@Valid @RequestBody UserRequestDTO request) {
        if (userRepository.existsByEmailAddress(request.getEmailAddress())) {
            return "User with this email already exists";
        }

        User user = new User(request.getUsername(), request.getEmailAddress(),
                passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserRequestDTO request) {
        User user = userRepository.findByEmailAddress(request.getEmailAddress())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtils.generateToken(user.getEmailAddress());
    }
}
