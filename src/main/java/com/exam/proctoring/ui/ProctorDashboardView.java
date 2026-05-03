package com.exam.proctoring.ui;

import com.exam.proctoring.domain.ProctoringEvent;
import com.exam.proctoring.service.ProctoringEventBroadcaster;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Route("admin/proctor/:attemptId")
@PageTitle("Live Proctoring Dashboard")
public class ProctorDashboardView extends VerticalLayout {

    private final Grid<ProctoringEvent> eventGrid = new Grid<>(ProctoringEvent.class, false);
    private final List<ProctoringEvent> events = new ArrayList<>();
    private final String attemptId;
    
    private final ProctoringEventBroadcaster broadcaster;
    private Consumer<ProctoringEvent> eventListener;

    public ProctorDashboardView(ProctoringEventBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
        this.attemptId = "some-test-attempt-id"; // In a real app, get this from the URL parameter

        addClassName("proctor-dashboard");
        setSizeFull();

        H1 title = new H1("Live Proctoring Dashboard");
        title.addClassName("dashboard-title");

        Span attemptIdSpan = new Span("Monitoring Attempt: " + attemptId);
        attemptIdSpan.addClassName("attempt-id-span");

        configureGrid();

        add(title, attemptIdSpan, eventGrid);
    }

    private void configureGrid() {
        eventGrid.addClassName("event-grid");
        eventGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        eventGrid.setSizeFull();

        eventGrid.addColumn(new ComponentRenderer<>(event -> {
            Icon icon;
            String severity = event.getSeverity();
            if ("CRITICAL".equals(severity)) {
                icon = VaadinIcon.WARNING.create();
                icon.setColor("var(--lumo-error-color)");
            } else if ("WARNING".equals(severity)) {
                icon = VaadinIcon.INFO_CIRCLE.create();
                icon.setColor("orange");
            } else {
                icon = VaadinIcon.CHECK_CIRCLE.create();
                icon.setColor("var(--lumo-success-color)");
            }
            return icon;
        })).setHeader("").setFlexGrow(0).setWidth("60px");

        eventGrid.addColumn(ProctoringEvent::getEventType).setHeader("Event Type").setAutoWidth(true);
        eventGrid.addColumn(event -> event.getTimestamp() != null ? event.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm:ss")) : "").setHeader("Timestamp").setAutoWidth(true);
        eventGrid.addColumn(event -> event.getMetadata() != null ? event.getMetadata().toString() : "").setHeader("Details").setFlexGrow(1);

        eventGrid.setItems(events);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        UI ui = attachEvent.getUI();
        
        eventListener = event -> {
            System.out.println("Broadcaster sent event to Dashboard: " + event.getEventType());
            // Only show events for the attempt being monitored
            if (attemptId.equals(event.getAttemptId())) {
                ui.access(() -> {
                    System.out.println("UI Access block executing, updating grid...");
                    events.add(0, event); // Add to the top of the list
                    // Re-set items to force a full grid refresh and ensure the new item appears
                    eventGrid.setItems(events);
                });
            }
        };
        
        broadcaster.register(eventListener);
        System.out.println("Proctor Dashboard registered to listen for events");
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        broadcaster.unregister(eventListener);
        System.out.println("Proctor Dashboard unregistered from events");
    }
}
