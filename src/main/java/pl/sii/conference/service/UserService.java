package pl.sii.conference.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sii.conference.domain.model.User;
import pl.sii.conference.domain.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void userLogIn(String login, String email) {
        if (checkIfUserExists(login, email)) {
            //TODO: login user
        } else {
            //TODO: change into notification
            throw new RuntimeException("You need to register first!!!");
        }
    }

    public void registerUser(String login, String email) {
        if(!checkIfUserExists(login, email)) {
            addUser(login, email);
        } else if (checkIfLoginExists(login)) {
            //TODO: change into notification
            throw new RuntimeException("Inserted login is already taken!!!");
        } else {
            //TODO: change into notification
            throw new RuntimeException("User already exists!!!");
        }
    }

    protected boolean checkIfLoginExists(String login) {
        Optional<User> user = userRepository.findUserByLogin(login);
        return user.isPresent();
    }

    protected void addUser(String login, String email) {
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        userRepository.save(user);
    }

    protected  boolean checkIfUserExists(String login, String email) {
        Optional<User> user = userRepository.findUserByLoginAndEmail(login, email);
        return user.isPresent();
    }

}
