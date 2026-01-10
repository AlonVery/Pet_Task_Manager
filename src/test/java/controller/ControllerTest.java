package controller;

import application.dto.out.user.RegisterUserDTOResponse;
import application.usecase.registration.UserRegistrationUseCase;
import domain.model.user.User;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepository;
import infra.db.in_memory_repository.InMemoryUserRepository;
import infra.security.PasswordEncoderSha256;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.controller.controllers.RegistrationUserController;
import web.http.request.Request;
import web.http.response.Response;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    static UserRepository repo;
    static PasswordEncoder encoder;
    static UserRegistrationUseCase useCase;
    static RegistrationUserController controller;

    @BeforeEach
    void setUp() {
        repo = new InMemoryUserRepository();
        encoder = new PasswordEncoderSha256();
        useCase = new UserRegistrationUseCase(repo, encoder);
        controller = new RegistrationUserController(useCase);
    }


    @Test
    void registrationController_success() throws Exception {
        Request requestJson = new Request();
//        String requestJson = """
//                {
//                  "userName": "Aboba",
//                  "email": "test@test.com",
//                  "password": "12345"
//                }
//                """;

        RegisterUserDTOResponse response = controller.handle(requestJson);
        String testResponse = "{\"message\":\"User register successfully\"}";

        User user = repo.findByUsername("Aboba").orElseThrow();
        String name = user.getUserName();
        String email = user.getEmail();

        assertNotNull(response);
        assertEquals(testResponse, response);
        assertEquals("Aboba", name);
        assertEquals("test@test.com", email);
    }

}
