package pl.sii.conference.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.sii.conference.view.UserSessionDetails;
import pl.sii.conference.domain.model.User;
import pl.sii.conference.domain.repository.UserRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSessionDetails userSessionDetails;

    public boolean userLogIn(@NotBlank String login, @NotBlank @Email String email) {
        Optional<User> userOptional = getUser(login, email);
        if (userOptional.isPresent()) {
            userSessionDetails.setLoggedIn(true);
            User existingUser = userOptional.get();
            userSessionDetails.setUser(existingUser);
            return true;
        } else {
            return false;
        }
    }

    public void userLogOut() {
        if (userSessionDetails.isLoggedIn()) {
            userSessionDetails.setUser(null);
            userSessionDetails.setLoggedIn(false);
        }
    }

    public RegistrationStatus registerUser(@NotBlank String login, @NotBlank @Email String email) {
        Optional<User> userOptional = getUser(login);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            if (existingUser.getEmail().equals(email)) {
                return RegistrationStatus.DUPLICATEUSER;
            } else {
                return RegistrationStatus.DUPLICATELOGIN;
            }
        } else {
            addUser(login, email);
            return RegistrationStatus.SUCCESS;
        }
    }

    public void changeUserEmail(@NotBlank @Email String newEmail) {
        if (userSessionDetails.isLoggedIn()) {
            User user = userSessionDetails.getUser();
            user.setEmail(newEmail);
            userRepository.save(user);
        }
    }

    private void addUser(String login, String email) {
        User user = new User(login, email);
        userRepository.save(user);
    }

    private Optional<User> getUser(String login, String email) {
        return userRepository.findUserByLoginAndEmail(login, email);
    }

    private Optional<User> getUser(String login) {
        return userRepository.findUserByLogin(login);
    }

}
