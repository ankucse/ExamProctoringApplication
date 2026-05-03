package com.exam.proctoring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Domain entity representing an Exam.
 */
@Document(collection = "exams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam {
    
    @Id
    private String id;
    
    private String title;
    
    // Duration in minutes
    private int duration;
    
    // ID of the admin who created it
    private String createdBy;
    
    // Embedded list of questions
    private List<Question> questions;
    
    @CreatedDate
    private LocalDateTime createdAt;
}
