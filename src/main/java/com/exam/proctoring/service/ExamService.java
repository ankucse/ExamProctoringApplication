package com.exam.proctoring.service;

import com.exam.proctoring.dto.ExamDTO;

import java.util.List;

public interface ExamService {
    ExamDTO createExam(ExamDTO examDTO, String createdBy);
    ExamDTO getExam(String id);
    List<ExamDTO> listExams();
}
