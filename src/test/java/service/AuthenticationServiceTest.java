package service;

import domain.service.AuthService;
import domain.service.UserService;
import infra.db.in_memory_repository.InMemoryUserRepository;
import infra.security.PasswordEncoderSha256;
import org.junit.jupiter.api.Test;

public class AuthenticationServiceTest {
    InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
    PasswordEncoderSha256 passwordEncoderSha256 = new PasswordEncoderSha256();
    UserService userService = new UserService(inMemoryUserRepository);
    AuthService authService = new AuthService(inMemoryUserRepository, passwordEncoderSha256);

    @Test
    public void test1Test1() {
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

        System.out.println(authService.login("Aboba", "Qwerty123!"));
        System.out.println(authService.login("test2", "Qasd23!"));


    }
}
