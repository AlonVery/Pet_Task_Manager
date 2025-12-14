package domain.model.user;

import domain.repository.PasswordEncoder;
import lombok.Getter;

public class PasswordHash {
    @Getter
    private final String value;

    private PasswordEncoder encoder;

    private PasswordHash(String value) {
        this.value = value;
    }

    public PasswordHash fromRow(String rowPassword, PasswordEncoder passwordEncoder) {
        return new PasswordHash(passwordEncoder.hashPassword(rowPassword));
    }

    public boolean matches(String rowPassword, String encodedPassword) {
        return encoder.checkPassword(rowPassword,  encodedPassword);
    }

}
