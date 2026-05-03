package com.exam.proctoring.dto;

import com.exam.proctoring.domain.Question;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ExamDTO {
    private String id;
    private String title;
    private int duration;
    private String createdBy;
    private List<Question> questions;
    private LocalDateTime createdAt;
}
