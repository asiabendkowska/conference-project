package pl.sii.conference.exception;

public class DuplicateUserRegistrationException extends RegistrationException {
    public DuplicateUserRegistrationException(String message) {
        super(message);
    }
}
