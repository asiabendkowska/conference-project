package pl.sii.conference.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.sii.conference.domain.model.Lecture;
import pl.sii.conference.domain.model.Reservation;
import pl.sii.conference.domain.model.User;
import pl.sii.conference.domain.repository.ReservationRepository;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EmailService emailService;
    private static final int MAX_SEATS = 5;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ReservationStatus makeReservation(User user, Lecture lecture) throws IOException {
        if (!checkIfUserTimeSlotTaken(user, lecture.getTimeSlotId())) {
            if (getLectureRemainingCapacity(lecture.getId()) > 0) {
                Reservation reservation = new Reservation();
                reservation.setUser(user);
                reservation.setLectureId(lecture.getId());
                reservation.setLectureTitle(lecture.getTitle());
                reservation.setTimeSlotId(lecture.getTimeSlotId());
                reservationRepository.save(reservation);
                emailService.sendConfirmation(user.getEmail(), lecture.getTitle());
                return ReservationStatus.SUCCESS;
            } else {
                return ReservationStatus.ALLSEATSTAKEN;
            }
        } else {
            return ReservationStatus.TIMESLOTTAKEN;
        }
    }

    public void removeReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public List<Reservation> getUserReservationList(User user) {
        return reservationRepository.findAllByUser(user);
    }

    private int getLectureRemainingCapacity(Integer lectureId) {
        Integer seatNumber = reservationRepository.countByLectureId(lectureId);
        return MAX_SEATS - seatNumber;
    }

    private boolean checkIfUserTimeSlotTaken(User user, Integer timeSlotId) {
        return reservationRepository.findByUserAndTimeSlotId(user, timeSlotId).isPresent();
    }

}