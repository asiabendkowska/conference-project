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

    public void submitUser(String login, String email) {
        if (checkIfUserExists(login, email)) {
            //TODO: login user
        } else {
            //TODO: check for existing login with wrong email
            addUser(login, email);
        }
    }

    public User addUser(String login, String email) {
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user = userRepository.save(user);
        return user;
    }

    public boolean checkIfUserExists(String login, String email) {
        Optional<User> user = userRepository.findUserByLoginAndEmail(login, email);
        return user.isPresent();
    }

}
