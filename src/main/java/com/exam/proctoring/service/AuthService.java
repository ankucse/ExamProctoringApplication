package com.exam.proctoring.service;

import com.exam.proctoring.dto.AuthRequest;
import com.exam.proctoring.dto.AuthResponse;
import com.exam.proctoring.dto.UserDTO;
import com.exam.proctoring.dto.UserRegistrationDTO;

public interface AuthService {
    UserDTO register(UserRegistrationDTO registrationDTO);
    AuthResponse login(AuthRequest authRequest);
}
