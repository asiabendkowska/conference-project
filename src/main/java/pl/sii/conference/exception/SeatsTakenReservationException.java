package pl.sii.conference.exception;

public class SeatsTakenReservationException extends ReservationException {
    public SeatsTakenReservationException(String message) {
        super(message);
    }
}
