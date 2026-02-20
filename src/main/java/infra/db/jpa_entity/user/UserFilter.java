package infra.db.jpa_entity.user;

import domain.model.user.UserStatus;

import java.time.Instant;

public record UserFilter(UserStatus status, Instant createdAt) {
}
