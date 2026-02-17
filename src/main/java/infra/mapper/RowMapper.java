package infra.mapper;

import domain.model.user.User;
import domain.model.user.UserRole;
import domain.model.user.UserStatus;
import infra.security.PasswordHash;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public final class RowMapper {
    public RowMapper() {
    }

    public static Optional<User> mapRow(ResultSet rs) throws SQLException {
        if (rs.next()) {
            UUID id = UUID.fromString(rs.getString("userId"));
            PasswordHash password = new PasswordHash(rs.getString("password_hash"));
            UserStatus status = UserStatus.valueOf(rs.getString("user_status").toUpperCase());
            Instant createdAt = rs.getTimestamp("created_at").toInstant();
            Instant updatedAt = rs.getTimestamp("updated_at").toInstant();
            return Optional.of(new User(
                    id,
                    rs.getString("user_name"),
                    rs.getString("email"),
                    password,
                    status,
                    UserRole.valueOf(rs.getString("user_role").toUpperCase()),
                    createdAt,
                    updatedAt
            ));
        }
        return Optional.empty();
    }
}
