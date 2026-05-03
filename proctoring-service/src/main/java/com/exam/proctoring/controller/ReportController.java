package com.exam.proctoring.controller;

import com.exam.proctoring.service.ReportingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportingService reportingService;

    public ReportController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/{attemptId}")
    public ResponseEntity<Map<String, Object>> getAttemptReport(@PathVariable String attemptId) {
        return ResponseEntity.ok(reportingService.generateReport(attemptId));
    }
}
