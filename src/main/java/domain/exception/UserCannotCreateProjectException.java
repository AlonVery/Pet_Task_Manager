package domain.exception;

public class UserCannotCreateProjectException extends RuntimeException {
  public UserCannotCreateProjectException(String message) {
    super(message);
  }
}
