package web.handler;

import application.dto.out.user.AuthDTOResponse;
import domain.exception.UserNotFoundException;
import tools.jackson.databind.ObjectMapper;
import web.controller.controllers.AuthorizationController;
import web.http.request.Request;
import web.http.response.Response;

public class AuthorizationHandler implements Handler {

    private final AuthorizationController authorizationController;
    private final ObjectMapper jsonMapper = new ObjectMapper();


    public AuthorizationHandler(AuthorizationController authorizationController) {
        this.authorizationController = authorizationController;
    }

    @Override
    public Response handle(Request request) {
        try {
            // 1. Application result
            AuthDTOResponse dto = authorizationController.handle(request);

            // 2. DTO → JSON
            byte[] body = jsonMapper
                    .writeValueAsBytes(dto);

            // 3. HTTP Response
            Response response = new Response();
            response.setStatus(200); // CREATED
            response.addHeader("Content-Type", "application/json");
            response.setBody(body);
            return response;

            //#todo: Handler — правильное место для маппинга ошибок в HTTP (400 / 409 / 422 / 500)
        }catch (UserNotFoundException e) {
            return Response.notFound();
        }
        catch (IllegalArgumentException e) {
            return Response.badRequest();

        } catch (Exception e) {
            return Response.internalError();
        }
    }
}
