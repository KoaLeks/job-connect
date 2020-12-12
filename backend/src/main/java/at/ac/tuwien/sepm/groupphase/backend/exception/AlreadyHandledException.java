package at.ac.tuwien.sepm.groupphase.backend.exception;

public class AlreadyHandledException extends RuntimeException{
    public AlreadyHandledException() {
    }

    public AlreadyHandledException(String message) {
        super(message);
    }

    public AlreadyHandledException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyHandledException(Exception e) {
        super(e);
    }
}
