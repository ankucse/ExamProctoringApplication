package com.exam.proctoring.service;

import com.exam.proctoring.domain.Role;
import com.exam.proctoring.domain.User;
import com.exam.proctoring.dto.UserRegistrationDTO;
import com.exam.proctoring.mapper.UserMapper;
import com.exam.proctoring.repository.UserRepository;
import com.exam.proctoring.security.JwtUtil;
import com.exam.proctoring.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRegisterUserAlreadyExists() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("test@test.com");
        dto.setName("Test");
        dto.setPassword("password");
        dto.setRole(Role.STUDENT);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> authService.register(dto));
        
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterUserSuccess() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("test@test.com");
        dto.setName("Test");
        dto.setPassword("password");
        dto.setRole(Role.STUDENT);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        authService.register(dto);
        
        verify(userRepository, times(1)).save(any(User.class));
    }
}
