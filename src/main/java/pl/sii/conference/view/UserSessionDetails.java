package pl.sii.conference.view;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
public class UserSessionDetails {
    private boolean loggedIn = false;
    private String login;
}
