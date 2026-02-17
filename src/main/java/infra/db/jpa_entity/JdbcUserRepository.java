package infra.db.jpa_entity;

import domain.exception.UserIsNotActiveException;
import domain.model.user.User;
import domain.model.user.UserRole;
import domain.model.user.UserStatus;
import domain.repository.UserRepository;
import infra.db.DataSource;
import infra.mapper.RowMapper;
import infra.security.PasswordHash;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcUserRepository implements UserRepository {

    @Override
    public void save(User user) {
        String saveUser = """
                INSERT INTO users (
                                   userId,
                                   user_name,
                                   email,
                                   password_hash,
                                   user_status,
                                   user_role,
                                   created_at,
                                   updated_at
                                   ) VALUES (?, ?, ?, ?, ?, ?, ?,?)
                """;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(saveUser)) {
            ps.setObject(1, user.getUserId());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPasswordHash().getValue());
            ps.setObject(5, user.getUserStatus().name(), Types.OTHER);
            ps.setObject(6, user.getUserRole().name(), Types.OTHER);
            ps.setObject(7, Timestamp.from(user.getCreatedAt()));
            ps.setObject(8, Timestamp.from(user.getUpdatedAt()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(UUID userId) {

        String deleteUser = "DELETE FROM users WHERE userId = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteUser)) {

            ps.setObject(1, userId);
            int rs = ps.executeUpdate();
            return rs > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Can't delete user" + e);
        }
    }

    @Override
    public Optional<User> findById(UUID id) {

        String query = "SELECT * FROM users WHERE userId = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setObject(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return RowMapper.mapRow(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by userId: " + id, e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {

        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return RowMapper.mapRow(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by email: " + email, e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {

        String query = "SELECT * FROM users WHERE user_name = ?";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                return RowMapper.mapRow(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByEmailAndActiveTrue(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserStatus status = UserStatus.valueOf(rs.getString("user_status").toUpperCase());
                    if (status.equals(UserStatus.ACTIVE)) {
                        UUID id = UUID.fromString(rs.getString("userId"));
                        PasswordHash password = new PasswordHash(rs.getString("password_hash"));
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
                    } else {
                        throw new UserIsNotActiveException("User isn't active by: " + email);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by email: " + email, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserStatus status = UserStatus.valueOf(rs.getString("user_status").toUpperCase());
                    UserRole role = UserRole.valueOf(rs.getString("user_role").toUpperCase());
                    if (status.equals(UserStatus.ACTIVE) && role.equals(UserRole.USER)) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error find user by email: " + email, e);
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            List<User> list = new ArrayList<>();

            while (rs.next()) {
                PasswordHash password = new PasswordHash(rs.getString("password_hash"));
                Instant createdAt = rs.getTimestamp("created_at").toInstant();
                Instant updatedAt = rs.getTimestamp("updated_at").toInstant();
                User user = new User(
                        UUID.fromString(rs.getString("userId")),
                        rs.getString("user_name"),
                        rs.getString("email"),
                        password,
                        UserStatus.valueOf(rs.getString("user_status").toUpperCase()),
                        UserRole.valueOf(rs.getString("user_role").toUpperCase()),
                        createdAt,
                        updatedAt);

                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
