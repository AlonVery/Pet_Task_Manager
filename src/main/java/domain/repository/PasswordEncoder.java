package domain.repository;

public interface PasswordEncoder {
    String hashPassword(String rawPassword);
    boolean checkPassword(String rawPassword, String encodedPassword);
}
