package at.ac.tuwien.sepm.groupphase.backend.exception;

public class PasswordsNotMatchingException extends RuntimeException {
    public PasswordsNotMatchingException() {
    }

    public PasswordsNotMatchingException(String message) {
        super(message);
    }
}
