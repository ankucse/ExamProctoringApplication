package com.exam.proctoring.config;

/**
 * Deprecated: Pivoted to InternalEventService (Virtual Threads) instead of Kafka.
 * Class kept to avoid compilation file-not-found issues since we can't easily delete files via the IDE tool right now.
 */
public class KafkaConfig {
    public static final String PROCTORING_EVENTS_TOPIC = "proctoring-events";
}