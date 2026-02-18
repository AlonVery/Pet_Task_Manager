package web.router;


import web.handler.Handler;
import web.http.request.Request;
import web.http.response.Response;

import java.util.ArrayList;
import java.util.List;

public class Router {
    private final List<Route> routes = new ArrayList<>();

    public Response route(Request request) {
        for (Route route : routes) {
            if (route.matches(request)) {
                route.fillPathParams(request);
                return route.handler().handle(request);
            }
        }
        return Response.notFound();
    }

    public void addRoute(String method, String path, Handler handler) {
        routes.add(new Route(method, path, handler));
    }
}
