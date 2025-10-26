package com.bookstore.serviceImpl;

import com.bookstore.dto.UserRequestDTO;
import com.bookstore.dto.UserResponseDTO;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import com.bookstore.security.JwtUtil;
import com.bookstore.service.AuthService;

import lombok.AllArgsConstructor;
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

        // Default to CUSTOMER if role not provided
        if (userRequest.getRole() == null) {
            userRequest.setRole(com.bookstore.enumClassess.Role.CUSTOMER);
        } else {
            // Important: Do not allow public registration as ADMIN in production.
            // If you want to allow admin creation, protect this endpoint or use a seed/admin creation flow.
            if (userRequest.getRole() == com.bookstore.enumClassess.Role.ADMIN) {
                throw new IllegalArgumentException("Cannot create ADMIN via public register endpoint");
            }
        }

        // Convert to entity, then encode password on entity (avoid mutating DTO as best practice)
        User user = UserRequestDTO.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return jwtUtil.generateToken(email, user.getRole().name());
    }
}
