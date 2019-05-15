package pl.sii.conference.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sii.conference.view.UserSessionDetails;
import pl.sii.conference.domain.model.User;
import pl.sii.conference.domain.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSessionDetails userSessionDetails;

    public boolean userLogIn(String login, String email) {
        Optional<User> userOptional = getUser(login, email);
        if (userOptional.isPresent()) {
            userSessionDetails.setLoggedIn(true);
            User user = userOptional.get();
            userSessionDetails.setUser(user);
            return true;
        } else {
            return false;
        }
    }

    public RegistrationStatus registerUser(String login, String email) {
        Optional<User> userOptional = getUser(login);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getEmail().equals(email)) {
                return RegistrationStatus.DUPLICATEUSER;
            } else {
                return RegistrationStatus.DUPLICATELOGIN;
            }
        } else {
            addUser(login, email);
            return RegistrationStatus.SUCCESS;
        }
    }
    protected void addUser(String login, String email) {
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        userRepository.save(user);
    }

    private Optional<User> getUser(String login, String email) {
        return userRepository.findUserByLoginAndEmail(login, email);
    }

    private Optional<User> getUser(String login) {
        return userRepository.findUserByLogin(login);
    }

}
