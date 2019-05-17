package pl.sii.conference.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.sii.conference.domain.model.Lecture;
import pl.sii.conference.domain.model.Reservation;
import pl.sii.conference.domain.model.User;
import pl.sii.conference.domain.repository.ReservationRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ReservationService reservationService;

    private User existingUser = new User("joe", "joe@gmail.com");
    private Lecture lecture = new Lecture(1, "React", 1, 1);
    private Reservation reservation = new Reservation(null, 1, existingUser, 1);

    @Test
    public void testMakeReservation_GivenCorrectData_ExpectSuccessReservation()throws IOException {
        when(reservationRepository.countByLectureId(lecture.getId())).thenReturn(1);
        when(reservationRepository.findByUserAndTimeSlotId(existingUser, lecture.getTimeSlotId())).thenReturn(Optional.empty());
        ReservationStatus reservationStatus = reservationService.makeReservation(existingUser, lecture);
        Assert.assertEquals(ReservationStatus.SUCCESS, reservationStatus);
        verify(reservationRepository, times(1)).save(reservation);
        verify(emailService, times(1)).sendConfirmation(existingUser.getEmail(), lecture.getTitle());
    }

    @Test
    public void testMakeReservation_GivenAllSeatsTaken_ExpectAllSeatsTakenStatus() throws IOException {
        when(reservationRepository.countByLectureId(lecture.getId())).thenReturn(5);
        ReservationStatus reservationStatus = reservationService.makeReservation(existingUser, lecture);
        Assert.assertEquals(ReservationStatus.ALLSEATSTAKEN, reservationStatus);
        verify(reservationRepository, times(0)).save(reservation);
        verify(emailService, times(0)).sendConfirmation(existingUser.getEmail(), lecture.getTitle());

    }

    @Test
    public void testMakeReservation_GivenTimeSlotTaken_ExpectTimeSlotTakenStatus() throws IOException {
        when(reservationRepository.findByUserAndTimeSlotId(existingUser,reservation.getTimeSlotId())).thenReturn(Optional.of(reservation));
        ReservationStatus reservationStatus = reservationService.makeReservation(existingUser, lecture);
        Assert.assertEquals(ReservationStatus.TIMESLOTTAKEN, reservationStatus);
        verify(reservationRepository, times(0)).save(reservation);
        verify(emailService, times(0)).sendConfirmation(existingUser.getEmail(), lecture.getTitle());
    }

    @Test
    public void testRemoveReservation_GivenExistingUser_ExpectRemove() {
        reservationService.removeReservation(reservation.getId());
        verify(reservationRepository,times(1)).deleteById(reservation.getId());
    }

    @Test
    public void testGetUserReservationList_GivenTwoReservationsForExistingUser_ExpectBothReservationsReturned() throws IOException {
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservation);
        reservationList.add(new Reservation(null, 2, existingUser, 2));
        doReturn(reservationList).when(reservationRepository).findAllByUser(existingUser);
        List<Reservation> userReservationList = reservationService.getUserReservationList(existingUser);
        Assert.assertEquals(2, userReservationList.size());
        verify(reservationRepository,times(1)).findAllByUser(existingUser);
    }
}
