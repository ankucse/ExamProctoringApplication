package com.exam.proctoring.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("student/dashboard")
public class StudentDashboardView extends VerticalLayout {

    public StudentDashboardView() {
        add(new H1("Student Dashboard"));
        // TODO: List available exams
    }
}
