package pl.sii.conference.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sii.conference.exception.DuplicateLoginRegistrationException;
import pl.sii.conference.exception.DuplicateUserRegistrationException;
import pl.sii.conference.exception.UserNotFoundException;
import pl.sii.conference.view.UserSessionDetails;
import pl.sii.conference.domain.model.User;
import pl.sii.conference.domain.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSessionDetails userSessionDetails;

    public void userLogIn(String login, String email) throws UserNotFoundException {
        User user = getUser(login, email).orElseThrow(() -> new UserNotFoundException("You need to register first!"));
        userSessionDetails.setLoggedIn(true);
        userSessionDetails.setUser(user);
    }

    public void registerUser(String login, String email) throws DuplicateUserRegistrationException, DuplicateLoginRegistrationException {
        if (checkIfLoginExists(login)){
            String message;
            if (checkIfUserExists(login, email)) {
                //TODO: is this if really needed?
                throw new DuplicateUserRegistrationException("User already exists!");
            } else {
                throw new DuplicateLoginRegistrationException("Inserted login is already taken!");
            }
        } else {
            addUser(login, email);
        }

        /*if(!checkIfUserExists(login, email)) {
            addUser(login, email);
        } else if (checkIfLoginExists(login)) {
            //TODO: change into notification
            Notification.show("Inserted login is already taken!");
            //throw new RuntimeException("Inserted login is already taken!");
        } else {
            //TODO: change into notification
            Notification.show("User already exists!");
            //throw new RuntimeException("User already exists!");
        }*/
    }

    void addUser(String login, String email) {
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        userRepository.save(user);
    }

    Optional<User> getUser(String login, String email) {
        return userRepository.findUserByLoginAndEmail(login, email);
    }

    boolean checkIfLoginExists(String login) {
        Optional<User> user = userRepository.findUserByLogin(login);
        return user.isPresent();
    }

    boolean checkIfUserExists(String login, String email) {
        Optional<User> user = userRepository.findUserByLoginAndEmail(login, email);
        return user.isPresent();
    }

}
