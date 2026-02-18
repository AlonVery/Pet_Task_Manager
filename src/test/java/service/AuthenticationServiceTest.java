package service;

import domain.service.AuthService;
import domain.service.UserService;
import infra.db.jpa_entity.JdbcUserRepository;
import infra.security.PasswordEncoderSha256;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AuthenticationServiceTest {
    JdbcUserRepository inMemoryUserRepository = new JdbcUserRepository();
    PasswordEncoderSha256 passwordEncoderSha256 = new PasswordEncoderSha256();
    UserService userService = new UserService(inMemoryUserRepository);
    AuthService authService = new AuthService(inMemoryUserRepository, passwordEncoderSha256);

    @Test
    public void test1() {
        Assertions.assertNotNull(userService.getAllUsers().size());
        Assertions.assertNotNull(authService.login("Email@efe.dsf", "Qwerty123!"));
    }
}
