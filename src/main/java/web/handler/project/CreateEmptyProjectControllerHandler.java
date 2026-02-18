package web.handler.project;

import application.dto.out.project.CreateEmptyProjectDTOResponse;
import tools.jackson.databind.ObjectMapper;
import web.controller.controllers.project.CreateEmptyProjectController;
import web.handler.Handler;
import web.http.request.Request;
import web.http.response.Response;

public class CreateEmptyProjectControllerHandler implements Handler {

    private final CreateEmptyProjectController controller;
    private final ObjectMapper jsonMapper = new ObjectMapper();

    public CreateEmptyProjectControllerHandler(CreateEmptyProjectController createEmptyProjectController) {
        this.controller = createEmptyProjectController;
    }

    @Override
    public Response handle(Request request) {
        try {
            // 1. Application result
            CreateEmptyProjectDTOResponse dto = controller.handle(request);

            // 2. DTO → JSON
            byte[] body = jsonMapper
                    .writeValueAsBytes(dto);

            // 3. HTTP Response
            new Response();
            Response response = Response.created(body);
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
