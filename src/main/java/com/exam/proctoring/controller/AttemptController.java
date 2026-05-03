package com.exam.proctoring.controller;

import com.exam.proctoring.domain.Answer;
import com.exam.proctoring.domain.Attempt;
import com.exam.proctoring.service.AttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attempts")
public class AttemptController {

    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping("/start/{examId}")
    public ResponseEntity<Attempt> startExam(@PathVariable String examId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return ResponseEntity.ok(attemptService.startExam(currentPrincipalName, examId));
    }

    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<Attempt> submitExam(@PathVariable String attemptId) {
        return ResponseEntity.ok(attemptService.submitExam(attemptId));
    }

    @PostMapping("/{attemptId}/answers")
    public ResponseEntity<Answer> saveAnswer(
            @PathVariable String attemptId,
            @RequestParam String questionId,
            @RequestBody String response) {
        return ResponseEntity.ok(attemptService.saveAnswer(attemptId, questionId, response));
    }
}
