package server;

import application.usecase.registration.UserRegistrationUseCase;
import com.sun.net.httpserver.HttpServer;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepository;
import infra.db.in_memory_repository.InMemoryUserRepository;
import infra.security.PasswordEncoderSha256;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import web.controller.controllers.RegistrationUserController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SunNetHttpServerTest {

    /** todo:
     * Happy path
     * валидный JSON → 201
     * <p>
     * Ошибки
     * пустое тело → 400
     * невалидный JSON → 400
     * пользователь уже существует → 409
     * неправильный метод (GET) → 405
     * */

    static HttpServer server;
    static UserRegistrationUseCase registrationUseCase;

//    @BeforeAll
//    static void setUp() {
//        final UserRepository userRepository = new InMemoryUserRepository();
//        final PasswordEncoder encoder = new PasswordEncoderSha256();
//        registrationUseCase = new UserRegistrationUseCase(userRepository, encoder);
//        try {
//            server = HttpServer.create(new InetSocketAddress(0), 0);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        server.createContext("/users/registration", exchange -> {
//            if ("POST".equals(exchange.getRequestMethod())) {
//                try {
//                    // читаем тело запроса один раз
//                    String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
//
//                    // вызываем контроллер
//                    String responseBody = new RegistrationUserController(registrationUseCase).handle(requestBody);
//
//                    // отправляем ответ
//                    exchange.getResponseHeaders().add("Content-Type", "application/json");
//                    byte[] responseBytes = responseBody.getBytes(StandardCharsets.UTF_8);
//                    exchange.sendResponseHeaders(201, responseBytes.length);
//                    exchange.getResponseBody().write(responseBytes);
//
//                } catch (Exception e) {
//                    String error = "{\"error\":\"" + e.getMessage() + "\"}";
//                    exchange.getResponseHeaders().add("Content-Type", "application/json");
//                    byte[] errorBytes = error.getBytes(StandardCharsets.UTF_8);
//                    exchange.sendResponseHeaders(500, errorBytes.length);
//                    exchange.getResponseBody().write(errorBytes);
//
//                } finally {
//                    exchange.getResponseBody().close(); // закрываем поток в любом случае
//                }
//            }
//        });
//        server.start();
//        System.out.println("Server started at http://localhost:8080");
//    }

//    @AfterAll
//    static void tearDown() {
//        server.stop(0);
//    }

    @Test
    public void registrationAPITest() {
        try (HttpClient client = HttpClient.newHttpClient();) {

            String json = """
                    {
                      "userName": "Aboba",
                      "email": "test@test.com",
                      "password": "12345"
                    }
                    """;

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/users/registration"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(201, response.statusCode());
            assertNotNull(response.body());
            assertTrue(response.body().contains("User register successfully"));

        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void registration_duplicate_email() {
    }

    @Test
    void registration_invalid_json() {
    }

    @Test
    void registration_empty_body() {
    }

    @Test
    void registration_wrong_method() {
    }
}
