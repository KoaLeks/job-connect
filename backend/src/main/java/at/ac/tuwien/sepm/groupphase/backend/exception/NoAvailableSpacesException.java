package at.ac.tuwien.sepm.groupphase.backend.exception;

public class NoAvailableSpacesException extends RuntimeException {
    public NoAvailableSpacesException() {
    }

    public NoAvailableSpacesException(String message) {
        super(message);
    }

    public NoAvailableSpacesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAvailableSpacesException(Exception e) {
        super(e);
    }
}
