package pl.sii.conference.service;

import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sii.conference.domain.model.User;
import pl.sii.conference.domain.repository.UserRepository;

import java.util.Optional;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void init() {
        Optional<User> userOptional = userRepository.findUserByLogin("test");
        if(userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        }
    }

    @Test
    public void testUserRegistration() {
        User user = new User("test", "test@gmail.com");

        RegistrationStatus status = userService.registerUser(user.getLogin(), user.getEmail());
        Assert.assertEquals(RegistrationStatus.SUCCESS, status);

        RegistrationStatus status2 = userService.registerUser(user.getLogin(), user.getEmail());
        Assert.assertEquals(RegistrationStatus.DUPLICATEUSER, status2);

        RegistrationStatus status3 = userService.registerUser(user.getLogin(), "test2@gmail.com");
        Assert.assertEquals(RegistrationStatus.DUPLICATELOGIN, status3);

        try {
            userService.registerUser("", user.getEmail());
            fail();
        } catch (ConstraintViolationException e) {
            Assert.assertEquals("registerUser.login: must not be blank", e.getMessage());
        }
        try {
            userService.registerUser(user.getLogin(), "");
            fail();
        } catch (ConstraintViolationException e) {
            Assert.assertEquals("registerUser.email: must not be blank", e.getMessage());
        }
        try {
            userService.registerUser(user.getLogin(), "test");
            fail();
        } catch (ConstraintViolationException e) {
            Assert.assertEquals("registerUser.email: must be a well-formed email address", e.getMessage());
        }
    }

    @Test
    public void testUserLogIn() {
        User user = new User("test", "test@gmail.com");
        Assert.assertFalse(userService.userLogIn(user.getLogin(), user.getEmail()));

        userService.registerUser(user.getLogin(), user.getEmail());
        Assert.assertTrue(userService.userLogIn(user.getLogin(), user.getEmail()));

        Assert.assertFalse(userService.userLogIn(user.getLogin(),"test2@gmail.com"));

        try {
            userService.userLogIn(user.getLogin(), "");
            fail();
        } catch (ConstraintViolationException e) {
            Assert.assertEquals("userLogIn.email: must not be blank", e.getMessage());
        }

        try {
            userService.userLogIn("", user.getEmail());
            fail();
        } catch (ConstraintViolationException e) {
            Assert.assertEquals("userLogIn.login: must not be blank", e.getMessage());
        }

    }

    @Test
    public void testChangeUserEmail(){
        String newEmail = "test2@gmail.com";
        User user = new User("test", "test@gmail.com");
        userService.registerUser(user.getLogin(), user.getEmail());
        userService.userLogIn(user.getLogin(), user.getEmail());
        userService.changeUserEmail(newEmail);
        userService.userLogOut();
        Assert.assertTrue(userService.userLogIn(user.getLogin(), newEmail));
        Assert.assertFalse(userService.userLogIn(user.getLogin(), user.getEmail()));
    }

}
