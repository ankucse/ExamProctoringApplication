package com.exam.proctoring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Domain entity representing a user's answer to a specific question during an attempt.
 */
@Document(collection = "answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {
    
    @Id
    private String id;
    
    @Indexed
    private String attemptId;
    
    private String questionId;
    
    private String response;
}
