package infra.security;

import domain.repository.PasswordEncoder;
import lombok.Getter;

public final class PasswordHash {
    @Getter
    private final String value;

    public PasswordHash(String value) {
        this.value = value;
    }

    public static PasswordHash fromRaw(
            String rawPassword,
            PasswordEncoder encoder
    ) {
        return new PasswordHash(encoder.hashPassword(rawPassword));
    }

    public boolean matches(
            String rawPassword,
            PasswordEncoder encoder
    ) {
        return encoder.checkPassword(rawPassword, value);
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "PasswordHash{" +
                "value='" + value + '\'' +
                '}';
    }
}
