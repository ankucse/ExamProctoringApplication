package com.exam.proctoring.controller;

import com.exam.proctoring.domain.ProctoringEvent;
import com.exam.proctoring.dto.ProctoringEventDTO;
import com.exam.proctoring.service.InternalEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * High-throughput REST Controller for ingesting cheating events from the browser.
 */
@RestController
@RequestMapping("/api/proctoring")
public class ProctoringEventController {

    private final InternalEventService internalEventService;

    public ProctoringEventController(InternalEventService internalEventService) {
        this.internalEventService = internalEventService;
    }

    /**
     * Receives event payload from browser JS, enriches it, and processes it internally.
     * Returns 202 ACCEPTED.
     */
    @PostMapping("/events")
    public ResponseEntity<Void> ingestEvent(@RequestBody ProctoringEventDTO eventDTO) {
        ProctoringEvent event = ProctoringEvent.builder()
                .attemptId(eventDTO.getAttemptId())
                .eventType(eventDTO.getEventType())
                .severity(eventDTO.getSeverity())
                .metadata(eventDTO.getMetadata())
                .timestamp(LocalDateTime.now()) // Stamp the time exactly when server received it
                .build();

        internalEventService.processEvent(event);
        
        return ResponseEntity.accepted().build();
    }
}
