package com.exam.proctoring.service;

import com.exam.proctoring.domain.ProctoringEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * AI-Based Cheating Detection Engine.
 * This service listens to the video chunk / snapshot events and processes them.
 */
@Service
public class AIDetectionService {

    private static final Logger log = LoggerFactory.getLogger(AIDetectionService.class);
    private final KafkaProducerService kafkaProducerService;

    public AIDetectionService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    /**
     * Listens for raw media uploads and passes them to the AI model.
     * Simulated OpenCV/TensorFlow processing.
     */
    @KafkaListener(topics = "media-uploads", groupId = "ai-detection-group")
    public void processMediaForCheating(String mediaUrl, String attemptId) {
        log.info("AI Engine received media for analysis: {}", mediaUrl);

        // --- SIMULATED AI COMPUTER VISION LOGIC ---
        // In reality, you would pass the S3 URL to a Python/TensorFlow microservice via gRPC,
        // or use Java bindings for OpenCV (e.g., org.bytedeco:javacv) to run Haar Cascades.
        
        boolean noFaceDetected = Math.random() < 0.05; // 5% chance of no face
        boolean multipleFaces = Math.random() < 0.02;  // 2% chance of multiple faces

        if (noFaceDetected) {
            log.warn("AI Detection: No Face Found for attempt {}", attemptId);
            publishAIEvent(attemptId, "NO_FACE_DETECTED", "CRITICAL");
        } else if (multipleFaces) {
            log.warn("AI Detection: Multiple Faces Found for attempt {}", attemptId);
            publishAIEvent(attemptId, "MULTIPLE_FACES_DETECTED", "CRITICAL");
        }
    }

    private void publishAIEvent(String attemptId, String type, String severity) {
        ProctoringEvent event = ProctoringEvent.builder()
                .attemptId(attemptId)
                .eventType(type)
                .severity(severity)
                .metadata(Map.of("source", "AI_ENGINE"))
                .build();
        
        // Push the high-confidence AI event back into the main event stream
        kafkaProducerService.sendProctoringEvent(event);
    }
}
