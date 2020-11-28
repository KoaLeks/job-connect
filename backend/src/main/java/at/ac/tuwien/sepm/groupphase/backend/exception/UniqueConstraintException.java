package at.ac.tuwien.sepm.groupphase.backend.exception;

public class UniqueConstraintException extends RuntimeException{
    public UniqueConstraintException() {
    }

    public UniqueConstraintException(String message) {
        super(message);
    }

    public UniqueConstraintException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueConstraintException(Exception e) {
        super(e);
    }
}
