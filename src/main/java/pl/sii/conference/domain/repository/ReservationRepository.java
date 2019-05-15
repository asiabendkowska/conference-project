package pl.sii.conference.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sii.conference.domain.model.Reservation;
import pl.sii.conference.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository <Reservation, Long> {
    Optional<Reservation> findByUserAndTimeSlotId(User user, Integer timeSlotId);
    Integer countByLectureId(Integer lectureId);
    List<Reservation> findAllByUser(User user);
}
