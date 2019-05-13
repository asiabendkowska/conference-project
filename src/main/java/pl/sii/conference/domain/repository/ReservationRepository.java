package pl.sii.conference.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sii.conference.domain.model.Reservation;

public interface ReservationRepository extends JpaRepository <Reservation, Long> {

}
