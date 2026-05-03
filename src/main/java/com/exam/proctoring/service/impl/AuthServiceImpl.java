package com.exam.proctoring.service.impl;

import com.exam.proctoring.domain.User;
import com.exam.proctoring.dto.AuthRequest;
import com.exam.proctoring.dto.AuthResponse;
import com.exam.proctoring.dto.UserDTO;
import com.exam.proctoring.dto.UserRegistrationDTO;
import com.exam.proctoring.mapper.UserMapper;
import com.exam.proctoring.repository.UserRepository;
import com.exam.proctoring.security.JwtUtil;
import com.exam.proctoring.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                           UserDetailsService userDetailsService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO register(UserRegistrationDTO registrationDTO) {
        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(registrationDTO.getName())
                .email(registrationDTO.getEmail())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .role(registrationDTO.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        
        User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow();
        return new AuthResponse(jwt, userMapper.toDTO(user));
    }
}
