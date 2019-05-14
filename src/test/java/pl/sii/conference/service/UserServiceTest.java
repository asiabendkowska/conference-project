package pl.sii.conference.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.sii.conference.domain.model.User;
import pl.sii.conference.domain.repository.UserRepository;
import pl.sii.conference.service.UserService;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    private User user = new User(null, "Jan Kowalski", "kowalski@gmail.com");

    @Before
    public void init() {
        doReturn(Optional.of(user)).when(userRepository).findUserByLoginAndEmail(user.getLogin(), user.getEmail());
    }

    @Test
    public void checkIfUserExists_UserExists(){
        Assert.assertTrue(userService.checkIfUserExists(user.getLogin(), user.getEmail()));
    }

    @Test
    public void checkIfUserExists_WrongLogin(){
        String login = "Test";
        Assert.assertFalse(userService.checkIfUserExists(login, user.getEmail()));
    }

    @Test
    public void checkIfUserExists_WrongEmail(){
        String email = "test";
        Assert.assertFalse(userService.checkIfUserExists(user.getLogin(), email));
    }

    @Test
    public void checkIfUserExists_EmptyEmail(){
        String email = "";
        Assert.assertFalse(userService.checkIfUserExists(user.getLogin(), email));
    }

    @Test
    public void checkIfUserExists_EmptyLogin(){
        String login = "";
        Assert.assertFalse(userService.checkIfUserExists(login, user.getEmail()));
    }

    @Test
    public void addUser_CorrectValues(){
        userService.addUser(user.getLogin(), user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void addUser_WrongEmail(){
        userService.addUser(user.getLogin(), "test");
        verify(userRepository, times(0)).save(user);
    }

    @Test
    public void addUser_WrongLogin(){
        userService.addUser("", user.getEmail());
        verify(userRepository, times(0)).save(user);
    }
}
