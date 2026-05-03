package com.exam.proctoring.controller;

import com.exam.proctoring.domain.ProctoringEvent;
import com.exam.proctoring.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proctoring")
public class ProctoringEventController {

    private final KafkaProducerService kafkaProducerService;

    public ProctoringEventController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/events")
    public ResponseEntity<Void> ingestEvent(@RequestBody ProctoringEvent event) {
        kafkaProducerService.sendProctoringEvent(event);
        return ResponseEntity.accepted().build();
    }
}
