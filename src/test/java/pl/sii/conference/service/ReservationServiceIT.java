package pl.sii.conference.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.sii.conference.domain.model.Lecture;
import pl.sii.conference.domain.model.User;
import pl.sii.conference.domain.repository.ReservationRepository;
import pl.sii.conference.domain.repository.UserRepository;
import pl.sii.conference.view.UserSessionDetails;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private UserSessionDetails userSessionDetails;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Before
    public void init() {
        reservationRepository.deleteAll();
        removeUsersFromDB();
        emailService.setFilePath("test.log");
    }

    @After
    public void clean() throws IOException {
        Files.deleteIfExists(Paths.get(emailService.getFilePath()));
    }

    @Test
    public void testTwoReservationsOnTheSameTime() throws IOException {
        List<User> users = registerAndLogInUsers(1);
        List<Lecture> allLectures = lectureService.getAllLectures();

        ReservationStatus reservationStatus = reservationService.makeReservation(users.get(0), allLectures.get(0));
        Assert.assertEquals(ReservationStatus.SUCCESS, reservationStatus);

        ReservationStatus reservationStatus2 = reservationService.makeReservation(users.get(0), allLectures.get(4));
        Assert.assertEquals(ReservationStatus.TIMESLOTTAKEN, reservationStatus2);

        List<String> lines = Files.readAllLines(Paths.get(emailService.getFilePath()));
        Assert.assertEquals(1, lines.size());
    }

    @Test
    public void testMultipleReservationsOnTheSameLecture() throws IOException {
        List<User> users = registerAndLogInUsers(6);
        List<Lecture> allLectures = lectureService.getAllLectures();

        ReservationStatus reservationStatus1 = reservationService.makeReservation(users.get(0), allLectures.get(0));
        Assert.assertEquals(ReservationStatus.SUCCESS, reservationStatus1);

        ReservationStatus reservationStatus2 = reservationService.makeReservation(users.get(1), allLectures.get(0));
        Assert.assertEquals(ReservationStatus.SUCCESS, reservationStatus2);

        ReservationStatus reservationStatus3 = reservationService.makeReservation(users.get(2), allLectures.get(0));
        Assert.assertEquals(ReservationStatus.SUCCESS, reservationStatus3);

        ReservationStatus reservationStatus4 = reservationService.makeReservation(users.get(3), allLectures.get(0));
        Assert.assertEquals(ReservationStatus.SUCCESS, reservationStatus4);

        ReservationStatus reservationStatus5 = reservationService.makeReservation(users.get(4), allLectures.get(0));
        Assert.assertEquals(ReservationStatus.SUCCESS, reservationStatus5);

        ReservationStatus reservationStatus6 = reservationService.makeReservation(users.get(5), allLectures.get(0));
        Assert.assertEquals(ReservationStatus.ALLSEATSTAKEN, reservationStatus6);

        List<String> lines = Files.readAllLines(Paths.get(emailService.getFilePath()));
        Assert.assertEquals(5, lines.size());

    }

    private List<User> registerAndLogInUsers(int number) {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            String login = "test" + i;
            String email = "test" + i + "@gmail.com";
            userService.registerUser(login, email);
            userService.userLogIn(login, email);
            User user = userSessionDetails.getUser();
            users.add(user);
        }
        return users;
    }

    private void removeUsersFromDB() {
        for (int i = 1; i <= 6; i++) {
            String login = "test" + i;
            Optional<User> userOptional = userRepository.findUserByLogin(login);
            if(userOptional.isPresent()) {
                userRepository.delete(userOptional.get());
            }
        }
    }
}
