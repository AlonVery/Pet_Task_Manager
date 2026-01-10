package web.http.response;

import application.dto.out.user.RegisterUserDTOResponse;
import tools.jackson.databind.ObjectMapper;
import web.controller.controllers.RegistrationUserController;
import web.handler.Handler;
import web.http.request.Request;

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
            byte[] body = jsonMapper
                    .writeValueAsBytes(dto);

            // 3. HTTP Response
            Response response = new Response();
            response.setStatus(201); // CREATED
            response.setHeaders("Content-Type", "application/json");
            response.setBody(body);

            return response;

            //#todo: Handler — правильное место для маппинга ошибок в HTTP (400 / 409 / 422 / 500)
        } catch (IllegalArgumentException e) {
            return Response.badRequest();

        } catch (Exception e) {
            return Response.internalError();
        }
    }
}
