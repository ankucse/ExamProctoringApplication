package com.exam.proctoring.service;

import com.exam.proctoring.domain.ProctoringEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Service to broadcast proctoring events directly to connected Vaadin UIs.
 */
@Service
public class ProctoringEventBroadcaster {

    private final List<Consumer<ProctoringEvent>> listeners = new CopyOnWriteArrayList<>();

    public void register(Consumer<ProctoringEvent> listener) {
        listeners.add(listener);
    }

    public void unregister(Consumer<ProctoringEvent> listener) {
        listeners.remove(listener);
    }

    public void broadcast(ProctoringEvent event) {
        for (Consumer<ProctoringEvent> listener : listeners) {
            try {
                listener.accept(event);
            } catch (Exception e) {
                // Ignore exceptions from detached or dead listeners
            }
        }
    }
}
