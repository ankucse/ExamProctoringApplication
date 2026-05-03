package com.exam.proctoring.repository;

import com.exam.proctoring.domain.ProctoringEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for ProctoringEvent entity.
 */
@Repository
public interface ProctoringEventRepository extends MongoRepository<ProctoringEvent, String> {
    
    /**
     * Finds events for a specific attempt sorted by time.
     * @param attemptId The attempt ID.
     * @return List of events.
     */
    List<ProctoringEvent> findByAttemptIdOrderByTimestampDesc(String attemptId);
}
