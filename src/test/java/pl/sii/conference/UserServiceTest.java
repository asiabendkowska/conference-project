package pl.sii.conference;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pl.sii.conference.domain.model.User;
import pl.sii.conference.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    UserService userService = new UserService();
    User user = new User(1L, "Jan Kowalski", "kowalski@gmail.com");

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
}
