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
import pl.sii.conference.view.UserSessionDetails;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserSessionDetails userSessionDetails;

    @InjectMocks
    private UserService userService;
    private User existingUser = new User("joe", "joe@gmail.com");

  @Before
    public void init() {
        doReturn(Optional.of(existingUser)).when(userRepository).findUserByLoginAndEmail(existingUser.getLogin(), existingUser.getEmail());
        doReturn(Optional.of(existingUser)).when(userRepository).findUserByLogin(existingUser.getLogin());
    }

    @Test
    public void testUserLogIn_GivenExistingUser_ExpectLoggedIn() {
        User newUser = new User("joe", "joe@gmail.com");
        boolean result = userService.userLogIn(newUser.getLogin(), newUser.getEmail());
        Assert.assertTrue(result);
        verify(userSessionDetails, times(1)).setLoggedIn(true);
        verify(userSessionDetails, times(1)).setUser(newUser);
    }

    @Test
    public void testUserLogIn_GivenNotExistingUser_ExpectNotLoggedIn() {
        User newUser = new User("tom", "tom@gmail.com");
        boolean result = userService.userLogIn(newUser.getLogin(), newUser.getEmail());
        Assert.assertFalse(result);
        verify(userSessionDetails, times(0)).setLoggedIn(true);
        verify(userSessionDetails, times(0)).setUser(newUser);
    }

    @Test
    public void testUserLogIn_GivenExistingLoginAndNotExistingEmail_ExpectNotLoggedIn() {
        User newUser = new User("joe", "tom@gmail.com");
        boolean result = userService.userLogIn(newUser.getLogin(), newUser.getEmail());
        Assert.assertFalse(result);
        verify(userSessionDetails, times(0)).setLoggedIn(true);
        verify(userSessionDetails, times(0)).setUser(newUser);
    }

    @Test
    public void testRegisterUser_GivenNotExistingLoginAndEmail_ExpectSuccessStatus() {
        User newUser = new User("tom", "tom@gmail.com");
        RegistrationStatus registrationStatus = userService.registerUser(newUser.getLogin(), newUser.getEmail());
        Assert.assertEquals(RegistrationStatus.SUCCESS, registrationStatus);
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    public void testRegisterUser_GivenNotExistingLoginAndExistingEmail_ExpectSuccessStatus() {
        User newUser = new User("tom", "joe@gmail.com");
        RegistrationStatus registrationStatus = userService.registerUser(newUser.getLogin(), newUser.getEmail());
        Assert.assertEquals(RegistrationStatus.SUCCESS, registrationStatus);
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    public void testRegisterUser_GivenExistingLoginAndNotExistingEmail_ExpectDuplicateLoginStatus() {
        User newUser = new User("joe", "tom@gmail.com");
        RegistrationStatus registrationStatus = userService.registerUser(newUser.getLogin(), newUser.getEmail());
        Assert.assertEquals(RegistrationStatus.DUPLICATELOGIN, registrationStatus);
        verify(userRepository, times(0)).save(newUser);
    }

    @Test
    public void testRegisterUser_GivenExistingLoginAndEmail_ExpectDuplicateUserStatus() {
        User newUser = new User("joe", "joe@gmail.com");
        RegistrationStatus registrationStatus = userService.registerUser(newUser.getLogin(), newUser.getEmail());
        Assert.assertEquals(RegistrationStatus.DUPLICATEUSER, registrationStatus);
        verify(userRepository, times(0)).save(newUser);
    }

    @Test
    public void testRegisterUser_GivenEmptyLogin_ExpectException() {
        User newUser = new User("", "tom@gmail.com");
        RegistrationStatus registrationStatus = userService.registerUser(newUser.getLogin(), newUser.getEmail());
        verify(userRepository, times(0)).save(newUser);
    }

    @Test
    public void testRegisterUser_GivenEmptyEmail_ExpectException() {
        User newUser = new User("tom", "");
        RegistrationStatus registrationStatus = userService.registerUser(newUser.getLogin(), newUser.getEmail());
        verify(userRepository, times(0)).save(newUser);
    }

}
