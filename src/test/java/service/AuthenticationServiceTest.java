package service;

import domain.service.AuthService;
import domain.service.UserService;
import infra.db.DataSource;
import infra.db.jpa_entity.JdbcUserRepository;
import infra.security.PasswordEncoderSha256;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthenticationServiceTest {
    final Connection connection = DataSource.getConnection();
    JdbcUserRepository inMemoryUserRepository = new JdbcUserRepository(connection);
    PasswordEncoderSha256 passwordEncoderSha256 = new PasswordEncoderSha256();
    UserService userService = new UserService(inMemoryUserRepository);
    AuthService authService = new AuthService(inMemoryUserRepository, passwordEncoderSha256);

    @Test
    public void test1() throws SQLException {
        connection.setAutoCommit(false);
        String dropUsers = "Drop table users_for_test";
        String createTaleUsers = """
                CREATE TABLE users_for_test
                (
                    userId        uuid PRIMARY KEY,
                    user_name     VARCHAR(100) UNIQUE NOT NULL,
                    email         TEXT UNIQUE  NOT NULL,
                    password_hash TEXT NOT NULL,
                    user_status   user_status  NOT NULL,
                    user_role     user_role NOT NULL,
                    created_at    TIMESTAMPTZ  NOT NULL,
                    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT now()
                );
                """;
        try (PreparedStatement psDrop = connection.prepareStatement(dropUsers);
             PreparedStatement psCreateTable = connection.prepareStatement(createTaleUsers)) {
            psDrop.execute();
            psCreateTable.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        authService.register(
                "Aboba",
                "Email@efe.dsf",
                "Qwerty123!");

        authService.register(
                "test1",
                "@efe.dsf",
                "Qwerty");

        authService.register(
                "test2",
                "e.dsf",
                "Qwerty123!");


        System.out.println(userService.getAllUsers());

       System.out.println(authService.login("Email@efe.dsf", "Qwerty123!"));

    }
}
