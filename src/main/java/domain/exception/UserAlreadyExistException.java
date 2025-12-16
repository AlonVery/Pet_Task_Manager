package domain.exception;

import java.util.Arrays;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        System.out.println("UserAlreadyExistException: " + message + Arrays.toString(getStackTrace()));
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UserAlreadyExistException() {
    }
}
