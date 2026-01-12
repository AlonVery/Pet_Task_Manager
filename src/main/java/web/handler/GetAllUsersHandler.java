package web.handler;

import application.dto.out.user.GetAllUsersDTOResponse;
import tools.jackson.databind.ObjectMapper;
import web.controller.controllers.GetAllUsersController;
import web.http.request.Request;
import web.http.response.Response;

public class GetAllUsersHandler implements Handler {

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final GetAllUsersController controller;

    public GetAllUsersHandler(GetAllUsersController controller) {
        this.controller = controller;
    }

    @Override
    public Response handle(Request request) {
        try {
            // 1. Application result
            GetAllUsersDTOResponse dto = controller.handle(request);

            // 2. DTO → JSON
            byte[] body = jsonMapper
                    .writeValueAsBytes(dto);

            // 3. HTTP Response
            Response response = new Response();
            response.setStatus(200); //OK
            response.addHeader("Content-Type", "application/json");
            response.setBody(body);
            return response;

            //#todo: Handler — правильное место для маппинга ошибок в HTTP (400 / 409 / 422 / 500)
        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
            return Response.badRequest();

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return Response.internalError();
        }
    }
}
