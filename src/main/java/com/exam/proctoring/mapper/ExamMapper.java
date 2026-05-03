package com.exam.proctoring.mapper;

import com.exam.proctoring.domain.Exam;
import com.exam.proctoring.dto.ExamDTO;
import org.springframework.stereotype.Component;

@Component
public class ExamMapper {

    public ExamDTO toDTO(Exam exam) {
        if (exam == null) {
            return null;
        }
        return ExamDTO.builder()
                .id(exam.getId())
                .title(exam.getTitle())
                .duration(exam.getDuration())
                .createdBy(exam.getCreatedBy())
                .questions(exam.getQuestions())
                .createdAt(exam.getCreatedAt())
                .build();
    }
}
