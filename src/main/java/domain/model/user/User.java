package domain.model.user;

import domain.repository.PasswordEncoder;
import infra.security.PasswordHash;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Роль — кто он, статус — в каком он состоянии.
 */


public class User {
    @Getter
    private static UUID userId;
    @Getter
    private String userName;
    @Getter
    private String email;
    @Getter
    private PasswordHash passwordHash;
    @Getter
    private UserStatus userStatus;
    @Getter
    private UserRole userRole;
    @Getter
    private Instant createdAt;
    @Getter
    private Instant updatedAt;

    private final Object lock =  new Object();

    public User(UUID userId,
                String userName,
                String email,
                PasswordHash passwordHash,
                UserStatus userStatus,
                UserRole userRole) {
        this.userId = Objects.requireNonNull(userId);
        this.userName = Objects.requireNonNull(userName);
        this.email = Objects.requireNonNull(email);
        this.passwordHash = passwordHash;
        this.userStatus = Objects.requireNonNull(userStatus);
        this.userRole = Objects.requireNonNull(userRole);
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public User() {}

    /* --------- Фабрика --------- */
    public static User createUser(
            String userName,
            String email,
            String rawPassword,
            UserRole userRole,
            PasswordEncoder encoder) {
        return new User(
                userId = UUID.randomUUID(),
                userName,
                email,
                PasswordHash.fromRaw(rawPassword, encoder),
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

    public boolean isPasswordValid(String rawPassword, PasswordEncoder encoder) {
       return passwordHash.matches(rawPassword,  encoder);
    }

    public void changeUserPassword(User user, String newPassword, PasswordEncoder encoder) {
        if (user.userRole != UserRole.ADMIN) {
            throw new IllegalArgumentException("Only admin can change user status");
        }
        synchronized (lock) {
            this.passwordHash = PasswordHash.fromRaw(newPassword, encoder);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }
}
