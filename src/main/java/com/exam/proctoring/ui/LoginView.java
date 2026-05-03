package com.exam.proctoring.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.exam.proctoring.dto.AuthRequest;
import com.exam.proctoring.dto.AuthResponse;
import com.exam.proctoring.service.AuthService;
import org.springframework.web.client.RestTemplate;

@Route("login")
@PageTitle("Login | Exam Platform")
public class LoginView extends VerticalLayout {

    private final AuthService authService;

    public LoginView(AuthService authService) {
        this.authService = authService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Exam Platform Login");

        TextField email = new TextField("Email");
        PasswordField password = new PasswordField("Password");

        Button loginButton = new Button("Login", e -> {
            try {
                AuthRequest request = new AuthRequest();
                request.setEmail(email.getValue());
                request.setPassword(password.getValue());
                
                AuthResponse response = authService.login(request);
                Notification.show("Login successful! Token: " + response.getToken());
                // In a real Vaadin app, you'd store this token in the session and navigate
            } catch (Exception ex) {
                Notification.show("Login failed: " + ex.getMessage());
            }
        });

        add(title, email, password, loginButton);
    }
}
