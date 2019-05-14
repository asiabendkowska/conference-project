package pl.sii.conference.view;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.sii.conference.domain.model.User;

@Component
@SessionScope
@Data
public class UserSessionDetails {
    private boolean loggedIn = false;
    private User user;
}
