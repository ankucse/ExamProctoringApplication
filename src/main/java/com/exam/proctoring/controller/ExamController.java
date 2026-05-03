package com.exam.proctoring.controller;

import com.exam.proctoring.dto.ExamDTO;
import com.exam.proctoring.service.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@RequestBody ExamDTO examDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return new ResponseEntity<>(examService.createExam(examDTO, currentPrincipalName), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getExam(@PathVariable String id) {
        return ResponseEntity.ok(examService.getExam(id));
    }

    @GetMapping
    public ResponseEntity<List<ExamDTO>> listExams() {
        return ResponseEntity.ok(examService.listExams());
    }
}
