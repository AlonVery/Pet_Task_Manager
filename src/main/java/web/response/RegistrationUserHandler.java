package web.response;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import web.controller.controllers.BaseController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RegistrationUserHandler {

    public RegistrationUserHandler(HttpServer server, HttpExchange exchange, BaseController controller) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String responseBody = controller.handle(requestBody);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(201, responseBody.getBytes().length);
            exchange.getResponseBody().write(responseBody.getBytes());

        } catch (Exception e) {
            String error = "{\"error\":\"" + e.getMessage() + "\"}";
            byte[] errorBytes = error.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(500, errorBytes.length);
            exchange.getResponseBody().write(errorBytes);
        } finally {
            exchange.getResponseBody().close();
        }
    }
}
