package com.bookstore.serviceImpl;

import com.bookstore.dto.UserRequestDTO;
import com.bookstore.dto.UserResponseDTO;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import com.bookstore.security.JwtUtil;
import com.bookstore.service.AuthService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // -------------------- Register --------------------
    @Override
    public UserResponseDTO register(UserRequestDTO userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Encode password
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        // Save user
        User user = UserRequestDTO.toEntity(userRequest);
        User saved = userRepository.save(user);

        return UserResponseDTO.fromEntity(saved);
    }

    // -------------------- Login --------------------
    @Override
    public String login(String email, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Generate JWT
        return jwtUtil.generateToken(email);
    }
}
