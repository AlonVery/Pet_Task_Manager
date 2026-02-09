package domain.model.user;

import domain.repository.PasswordEncoder;
import infra.security.PasswordHash;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * User — агрегат корень.
 * Роль — кто он, статус — в каком он состоянии.
 */
@Getter
@EqualsAndHashCode(of="userId")
public class User {

    private final UUID userId;
    private final String userName;
    private final String email;

    private PasswordHash passwordHash;
    private UserStatus userStatus;
    private UserRole userRole;

    private Instant createdAt;
    private Instant updatedAt;

    /* --------- Конструктор (private) --------- */
    private User(
            UUID userId,
            String userName,
            String email,
            PasswordHash passwordHash,
            UserStatus userStatus,
            UserRole userRole
    ) {
        this.userId = Objects.requireNonNull(userId);
        this.userName = Objects.requireNonNull(userName);
        this.email = Objects.requireNonNull(email);
        this.passwordHash = Objects.requireNonNull(passwordHash);
        this.userStatus = Objects.requireNonNull(userStatus);
        this.userRole = Objects.requireNonNull(userRole);

        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public User(UUID userId,
                String userName,
                String email,
                PasswordHash passwordHash,
                UserStatus userStatus,
                UserRole userRole,
                Instant createdAt,
                Instant updatedAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userStatus = userStatus;
        this.userRole = userRole;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /* --------- Фабрика --------- */
    public static User create(
            String userName,
            String email,
            String rawPassword,
            UserRole role,
            PasswordEncoder encoder
    ) {
        return new User(
                UUID.randomUUID(),
                userName,
                email,
                PasswordHash.fromRaw(rawPassword, encoder),
                UserStatus.ACTIVE,
                role
        );
    }

    /* --------- Доменная логика --------- */

    public void changeStatus(UserStatus newStatus, UserRole actorRole) {
        requireAdmin(actorRole);
        this.userStatus = Objects.requireNonNull(newStatus);
        touch();
    }

    public void changePassword(String newPassword, UserRole actorRole, PasswordEncoder encoder) {
        requireAdmin(actorRole);
        this.passwordHash = PasswordHash.fromRaw(newPassword, encoder);
        touch();
    }

    public boolean isPasswordValid(String rawPassword, PasswordEncoder encoder) {
        return passwordHash.matches(rawPassword, encoder);
    }

    private void requireAdmin(UserRole actorRole) {
        if (actorRole != UserRole.ADMIN) {
            throw new IllegalStateException("Only admin can perform this operation");
        }
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    @Override
    public String toString() {
        return "User{" +
               "userId=" + userId +
               ", userName='" + userName + '\'' +
               ", email='" + email + '\'' +
               ", passwordHash=" + passwordHash +
               ", userStatus=" + userStatus +
               ", userRole=" + userRole +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               '}' + '\n';
    }
}
