package com.exam.proctoring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Domain entity representing a user's attempt on an exam.
 */
@Document(collection = "attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attempt {
    
    @Id
    private String id;
    
    @Indexed
    private String userId;
    
    @Indexed
    private String examId;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private AttemptStatus status;
}
