package com.exam.proctoring.controller;

import com.exam.proctoring.dto.AuthRequest;
import com.exam.proctoring.dto.AuthResponse;
import com.exam.proctoring.dto.UserDTO;
import com.exam.proctoring.dto.UserRegistrationDTO;
import com.exam.proctoring.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        return new ResponseEntity<>(authService.register(registrationDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }
}
