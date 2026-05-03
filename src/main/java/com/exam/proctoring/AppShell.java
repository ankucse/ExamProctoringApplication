package com.exam.proctoring;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.shared.communication.PushMode;

/**
 * Configures the application's shell, including the theme.
 * This is the modern Vaadin approach for setting global UI configurations.
 */
@Theme("exam-proctoring-app")
@Push(PushMode.AUTOMATIC)
public class AppShell implements AppShellConfigurator {
}
