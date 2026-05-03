package com.exam.proctoring.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("student/exam")
@PageTitle("Active Exam")
public class StudentExamView extends VerticalLayout {

    public StudentExamView() {
        String testAttemptId = "some-test-attempt-id";

        addClassName("student-exam-view");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        VerticalLayout card = new VerticalLayout();
        card.addClassName("exam-card");
        card.setMaxWidth("600px");
        card.setPadding(true);
        card.setSpacing(true);

        H2 title = new H2("Exam in Progress");
        title.getStyle().set("margin-top", "0");

        HorizontalLayout warningBanner = new HorizontalLayout();
        warningBanner.addClassName("warning-banner");
        warningBanner.setAlignItems(Alignment.CENTER);
        warningBanner.add(VaadinIcon.EXCLAMATION_CIRCLE.create(), new Span("Monitoring Active"));
        
        Paragraph instructions = new Paragraph("Your session is being strictly proctored. Do not switch tabs, exit fullscreen, or right-click. Violations will be logged instantly.");
        instructions.getStyle().set("text-align", "center");

        Button startProctoringBtn = new Button("Acknowledge & Start", e -> {
            UI.getCurrent().getPage().executeJs(
                "window.attemptId = $0;" +
                "window.authHeader = 'Bearer dummy-token';" + 
                "function sendEvent(eventType, severity, metadata = {}) {" +
                "    const payload = {" +
                "        attemptId: window.attemptId," +
                "        eventType: eventType," +
                "        severity: severity," +
                "        metadata: metadata" +
                "    };" +
                "    fetch('/api/proctoring/events', {" +
                "        method: 'POST'," +
                "        headers: { 'Content-Type': 'application/json' }," + 
                "        body: JSON.stringify(payload)" +
                "    }).catch(err => console.error('Failed to send', err));" +
                "}" +
                "document.addEventListener('visibilitychange', () => {" +
                "    if (document.visibilityState === 'hidden') {" +
                "        sendEvent('TAB_SWITCH', 'WARNING', { state: 'hidden' });" +
                "    }" +
                "});" +
                "window.addEventListener('blur', () => {" +
                "    sendEvent('BLUR', 'WARNING', { state: 'lost_focus' });" +
                "});" +
                "document.addEventListener('copy', (e) => {" +
                "    sendEvent('COPY', 'INFO');" +
                "});" +
                "document.addEventListener('paste', (e) => {" +
                "    sendEvent('PASTE', 'CRITICAL');" +
                "});" +
                "document.addEventListener('contextmenu', (e) => {" +
                "    sendEvent('RIGHT_CLICK', 'INFO');" +
                "});" +
                "document.documentElement.requestFullscreen().catch(e => console.log(e));",
                testAttemptId
            );
            e.getSource().setText("Monitoring Started");
            e.getSource().setEnabled(false);
            warningBanner.getStyle().set("color", "var(--lumo-success-color)");
            warningBanner.removeAll();
            warningBanner.add(VaadinIcon.CHECK_CIRCLE.create(), new Span("Monitoring Started Successfully"));
        });
        startProctoringBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);

        card.add(title, warningBanner, instructions, startProctoringBtn);
        card.setAlignItems(Alignment.CENTER);

        add(card);
    }
}
