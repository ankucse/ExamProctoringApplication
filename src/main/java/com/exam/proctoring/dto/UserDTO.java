package com.exam.proctoring.dto;

import com.exam.proctoring.domain.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
}
