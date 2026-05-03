package com.exam.proctoring.service;

import com.exam.proctoring.domain.ProctoringEvent;
import com.exam.proctoring.repository.ProctoringEventRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates detailed Post-Exam Reports.
 */
@Service
public class ReportingService {

    private final ProctoringEventRepository eventRepository;
    private final RiskScoringService riskScoringService;

    public ReportingService(ProctoringEventRepository eventRepository, RiskScoringService riskScoringService) {
        this.eventRepository = eventRepository;
        this.riskScoringService = riskScoringService;
    }

    /**
     * Generates a comprehensive JSON report for a specific attempt.
     */
    public Map<String, Object> generateReport(String attemptId) {
        List<ProctoringEvent> events = eventRepository.findByAttemptIdOrderByTimestampDesc(attemptId);
        
        int totalRiskScore = 0;
        int criticalViolations = 0;
        
        for (ProctoringEvent event : events) {
            totalRiskScore += riskScoringService.calculatePenalty(event);
            if ("CRITICAL".equals(event.getSeverity())) {
                criticalViolations++;
            }
        }

        // Final Probability Calculation
        double cheatingProbability = Math.min(100.0, (totalRiskScore / 200.0) * 100);

        Map<String, Object> report = new HashMap<>();
        report.put("attemptId", attemptId);
        report.put("totalEventsCaptured", events.size());
        report.put("criticalViolations", criticalViolations);
        report.put("calculatedRiskScore", totalRiskScore);
        report.put("cheatingProbabilityPercent", cheatingProbability);
        
        // In a real system, you would save this summary back to MongoDB
        // and optionally generate a PDF using iText or Apache PDFBox.
        
        return report;
    }
}
