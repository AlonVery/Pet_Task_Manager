package domain.exception;

public class ProjectEmptyException extends RuntimeException {

    public ProjectEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectEmptyException(Throwable cause) {
        super(cause);
    }

    public ProjectEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProjectEmptyException(String name) {
        super("Project with name: "  + name + " is empty.");
    }

    public ProjectEmptyException() {
    }
}
