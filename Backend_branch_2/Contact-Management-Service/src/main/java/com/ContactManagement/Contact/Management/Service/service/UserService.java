package com.ContactManagement.Contact.Management.Service.service;

import com.ContactManagement.Contact.Management.Service.dto.request.UserRequestDTO;
import com.ContactManagement.Contact.Management.Service.dto.response.UserResponseDTO;

public interface UserService {

    // Register a new user
    String registerUser(UserRequestDTO user);  // Returns success message or user ID/email

    // Authenticate user by email and password
    boolean loginUser(String email, String password);  // Just authentication

    // Fetch user by email (for token generation, profile display, etc.)
    UserResponseDTO getUserByEmail(String email);

    // Login user and return a JWT/token
    String loginUserAndGetToken(String email, String rawPassword);
}
