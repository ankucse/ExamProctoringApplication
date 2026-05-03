package com.exam.proctoring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Domain entity representing a detected cheating event or status update.
 * Compound index on attemptId + timestamp for quick dashboard queries.
 */
@Document(collection = "proctoring_events")
@CompoundIndex(def = "{'attemptId': 1, 'timestamp': -1}", name = "attempt_timestamp_idx")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProctoringEvent {
    
    @Id
    private String id;
    
    // Links to Attempt Collection
    private String attemptId;
    
    // e.g., "TAB_SWITCH", "NO_FACE", "MULTIPLE_FACES", "DEVTOOLS_OPEN"
    private String eventType;
    
    // Server-received time
    private LocalDateTime timestamp;
    
    // "INFO", "WARNING", "CRITICAL"
    private String severity;
    
    // Any extra data (e.g., number of faces detected, or screenshot URL)
    private Map<String, Object> metadata;
}
