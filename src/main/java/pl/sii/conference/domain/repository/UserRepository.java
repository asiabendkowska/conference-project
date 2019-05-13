package pl.sii.conference.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sii.conference.domain.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findUserByLoginAndEmail(String login, String email);
}
