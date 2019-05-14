package pl.sii.conference.exception;

public class DuplicateLoginRegistrationException extends RegistrationException {
    public DuplicateLoginRegistrationException(String message) {
        super(message);
    }
}
