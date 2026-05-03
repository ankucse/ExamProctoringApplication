package com.exam.proctoring.service.impl;

import com.exam.proctoring.domain.Exam;
import com.exam.proctoring.dto.ExamDTO;
import com.exam.proctoring.mapper.ExamMapper;
import com.exam.proctoring.repository.ExamRepository;
import com.exam.proctoring.service.ExamService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final ExamMapper examMapper;

    public ExamServiceImpl(ExamRepository examRepository, ExamMapper examMapper) {
        this.examRepository = examRepository;
        this.examMapper = examMapper;
    }

    @Override
    public ExamDTO createExam(ExamDTO examDTO, String createdBy) {
        Exam exam = Exam.builder()
                .title(examDTO.getTitle())
                .duration(examDTO.getDuration())
                .questions(examDTO.getQuestions())
                .createdBy(createdBy)
                .createdAt(LocalDateTime.now())
                .build();
        
        exam = examRepository.save(exam);
        return examMapper.toDTO(exam);
    }

    @Override
    public ExamDTO getExam(String id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        return examMapper.toDTO(exam);
    }

    @Override
    public List<ExamDTO> listExams() {
        return examRepository.findAll().stream()
                .map(examMapper::toDTO)
                .collect(Collectors.toList());
    }
}
