package com.exam.proctoring.service;

import com.exam.proctoring.domain.ProctoringEvent;
import com.exam.proctoring.repository.ProctoringEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service responsible for processing events using Virtual Threads as a lightweight alternative to Kafka.
 */
@Service
public class InternalEventService {

    private static final Logger log = LoggerFactory.getLogger(InternalEventService.class);
    private final ProctoringEventRepository proctoringEventRepository;
    private final ProctoringEventBroadcaster broadcaster;
    
    // Executor using Virtual Threads (Java 21+) to handle massive concurrency without Kafka overhead
    private final ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

    public InternalEventService(ProctoringEventRepository proctoringEventRepository, ProctoringEventBroadcaster broadcaster) {
        this.proctoringEventRepository = proctoringEventRepository;
        this.broadcaster = broadcaster;
    }

    /**
     * Processes an event asynchronously using a virtual thread.
     *
     * @param event The event payload.
     */
    public void processEvent(ProctoringEvent event) {
        virtualThreadExecutor.submit(() -> {
            try {
                log.info("Processing event async: attemptId={}, type={}", event.getAttemptId(), event.getEventType());
                
                // 1. Store the raw event in MongoDB
                proctoringEventRepository.save(event);
                
                // 2. Broadcast the event directly to connected Vaadin views
                broadcaster.broadcast(event);
            } catch (Exception e) {
                log.error("Error processing proctoring event", e);
            }
        });
    }
}
