package com.exam.proctoring.service;

import com.exam.proctoring.domain.ProctoringEvent;
import org.springframework.stereotype.Service;

/**
 * Evaluates cheating probability based on incoming events.
 */
@Service
public class RiskScoringService {

    /**
     * Calculates a risk penalty based on event type.
     * @param event The proctoring event.
     * @return Score penalty.
     */
    public int calculatePenalty(ProctoringEvent event) {
        return switch (event.getEventType()) {
            case "TAB_SWITCH", "BLUR" -> 10;
            case "COPY_PASTE" -> 20;
            case "DEVTOOLS_OPEN" -> 50;
            case "NO_FACE" -> 15;
            case "MULTIPLE_FACES" -> 40;
            default -> 0;
        };
    }
}
