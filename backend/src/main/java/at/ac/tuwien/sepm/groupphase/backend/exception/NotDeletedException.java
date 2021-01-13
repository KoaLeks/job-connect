package at.ac.tuwien.sepm.groupphase.backend.exception;

public class NotDeletedException extends RuntimeException {

    public NotDeletedException() {
    }

    public NotDeletedException(String message) {
        super(message);
    }

    public NotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotDeletedException(Exception e) {
        super(e);
    }

}
