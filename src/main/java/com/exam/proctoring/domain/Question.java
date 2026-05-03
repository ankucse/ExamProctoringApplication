package com.exam.proctoring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Domain entity representing a question in an exam.
 * We are embedding Questions inside the Exam document since they are tightly coupled and bounded in size.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    
    @Id
    private String id;
    
    private String text;
    
    private QuestionType type;
    
    // For MCQ questions
    private List<String> options;
    
    // For automatic evaluation (optional)
    private String correctAnswer;
}
