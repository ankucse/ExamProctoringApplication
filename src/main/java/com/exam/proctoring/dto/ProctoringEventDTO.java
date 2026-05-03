package com.exam.proctoring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

/**
 * Incoming REST/WebSocket Payload.
 */
@Data
@NoArgsConstructor
public class ProctoringEventDTO {
    private String attemptId;
    private String eventType;
    private String severity;
    private Map<String, Object> metadata;
}
