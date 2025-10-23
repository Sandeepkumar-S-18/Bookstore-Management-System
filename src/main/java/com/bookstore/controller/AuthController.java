package com.bookstore.controller;

import com.bookstore.dto.UserRequestDTO;
import com.bookstore.dto.UserResponseDTO;
import com.bookstore.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    // -------------------- Register --------------------
    @Operation(summary = "Register a new user (Customer or Admin)")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // -------------------- Login --------------------
    @Operation(summary = "Login user and get JWT token")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDTO request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(token);
    }
}
