package com.bookstore.service;

import com.bookstore.dto.UserRequestDTO;
import com.bookstore.dto.UserResponseDTO;

public interface AuthService {
    UserResponseDTO register(UserRequestDTO userRequest);
    String login(String email, String password); // returns JWT token
}
