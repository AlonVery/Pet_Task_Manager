package domain.model.user;

import domain.repository.PasswordEncoder;
import infra.security.PasswordEncoderBCrypt;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Роль — кто он, статус — в каком он состоянии.
 */


public class User {
    @Getter
    private UUID userId;
    @Getter
    private final String userName;
    @Getter
    private final String email;
    @Getter
    private String passwordHash;
    @Getter
    private UserStatus userStatus;
    @Getter
    private final UserRole userRole;
    @Getter
    private final Instant createdAt;
    @Getter
    private Instant updatedAt;

    private Object lock;
    PasswordHash ph;
    PasswordEncoderBCrypt peBCrypt;


    private User(UUID userId,
                 String userName,
                 String email,
                 String passwordHash,
                 UserStatus userStatus,
                 UserRole userRole) {
        this.userId = Objects.requireNonNull(userId);
        this.userName = Objects.requireNonNull(userName);
        this.email = Objects.requireNonNull(email);
        this.passwordHash = Objects.requireNonNull(passwordHash);
        this.userStatus = Objects.requireNonNull(userStatus);
        this.userRole = Objects.requireNonNull(userRole);
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    /* --------- Фабрика --------- */
    public User createUser(
            String userName,
            String email,
            String passwordHash,
            UserRole userRole) {
        return new User(
                this.userId = UUID.randomUUID(),
                userName,
                email,
                ph.fromRow(passwordHash, peBCrypt).toString(),
                UserStatus.ACTIVE,
                userRole);
    }

    /* --------- Доменная логика --------- */
    public void changeUserStatus(UserStatus userStatus, User user) {
        if (user.userRole != UserRole.ADMIN) {
            throw new IllegalArgumentException("Only admin can change user status");
        }
        synchronized (lock) {
            this.userStatus = userStatus;
            this.updatedAt = Instant.now();
        }
    }

    public boolean isPasswordValid(User user, String rawPassword) {
       return ph.matches(rawPassword, user.passwordHash);
    }

    public void changeUserPassword(User user, String newPassword) {
        if (user.userRole != UserRole.ADMIN) {
            throw new IllegalArgumentException("Only admin can change user status");
        }
        synchronized (lock) {
            this.passwordHash = ph.fromRow(newPassword, peBCrypt).toString();
        }
    }
}
