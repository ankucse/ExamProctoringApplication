package com.exam.proctoring.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("admin/dashboard")
public class AdminDashboardView extends VerticalLayout {

    public AdminDashboardView() {
        add(new H1("Admin Dashboard"));
        // TODO: Create exam form, list exams
    }
}
