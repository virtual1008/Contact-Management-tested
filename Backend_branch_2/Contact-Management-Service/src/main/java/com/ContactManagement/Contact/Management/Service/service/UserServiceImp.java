package com.ContactManagement.Contact.Management.Service.service;

import com.ContactManagement.Contact.Management.Service.dto.request.UserRequestDTO;
import com.ContactManagement.Contact.Management.Service.dto.response.UserResponseDTO;

public class UserServiceImp implements UserService{
    @Override
    public String registerUser(UserRequestDTO user) {
        return null;
    }

    @Override
    public boolean loginUser(String email, String password) {
        return false;
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public String loginUserAndGetToken(String email, String rawPassword) {
        return null;
    }
}
