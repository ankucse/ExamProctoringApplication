package com.exam.proctoring.service;

import com.exam.proctoring.domain.ProctoringEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, ProctoringEvent> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, ProctoringEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProctoringEvent(ProctoringEvent event) {
        kafkaTemplate.send("proctoring-events", event.getAttemptId(), event);
    }
}
