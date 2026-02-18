package web.handler;

import application.dto.out.user.RegisterUserDTOResponse;

import domain.exception.UserAlreadyExistException;
import tools.jackson.databind.ObjectMapper;
import web.controller.controllers.RegistrationUserController;
import web.http.request.Request;
import web.http.response.Response;

import java.nio.charset.StandardCharsets;

public class RegistrationUserHandler implements Handler {

    private final RegistrationUserController controller;
    private final ObjectMapper jsonMapper = new ObjectMapper();

    public RegistrationUserHandler(RegistrationUserController controller) {
        this.controller = controller;
    }

    @Override
    public Response handle(Request request) {
        try {
            // 1. Application result
            RegisterUserDTOResponse dto = controller.handle(request);

            // 2. DTO → JSON
            byte[] body = jsonMapper.writeValueAsBytes(dto);

            // 3. HTTP Response
            Response response = Response.created(body);
            response.addHeader("Content-Type", "application/json");
            response.addHeader("test", "test");
            response.setBody(body);
            return response;

            //#todo: Handler — правильное место для маппинга ошибок в HTTP (400 / 409 / 422 / 500)
        } catch (UserAlreadyExistException e) {
            // 1. Application result
            RegisterUserDTOResponse dto = new RegisterUserDTOResponse(e.getMessage());
            // 2. DTO → JSON
            byte[] body = jsonMapper.writeValueAsBytes(dto);
            // 3. HTTP Response
            Response response = Response.conflictError(body);
            response.addHeader("Content-Type", "application/json");
            response.addHeader("test", "test");
            response.setBody(body);
            return response;
        } catch (RuntimeException e) {
            return Response.internalError();
        }
    }
}
