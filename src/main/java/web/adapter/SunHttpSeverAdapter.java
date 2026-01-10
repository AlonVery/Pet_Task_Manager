package web.adapter;

import com.sun.net.httpserver.HttpExchange;
import web.handler.HttpHandler;
import web.http.request.Request;
import web.http.response.Response;
import web.router.Router;

import java.io.IOException;

public class SunHttpSeverAdapter implements HttpHandler {
    private final Router router;

    public SunHttpSeverAdapter(Router router) {
        this.router = router;
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        Request request = toRequest(httpExchange);
        Response response = router.route(request);
        try {
            httpExchange.sendResponseHeaders(response.getStatus(), response.getBody().length);
            httpExchange.getResponseBody().write(response.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Request toRequest(HttpExchange httpExchange) {
        Request request = new  Request();
        request.httpMethod = httpExchange.getRequestMethod();
        request.path = httpExchange.getRequestURI().getPath();
        request.headers = httpExchange.getRequestHeaders();
        return request;
    }

//    public void start(int port, Router router) {
//        try {
//            server = HttpServer.create(new InetSocketAddress(port), 0);
//
//            Request request = new Request(
//                    exchange.getRequestMethod(),
//                    exchange.getRequestURI().getPath(),
//                    exchange.getRequestHeaders(),
//                    exchange.getRequestBody().readAllBytes(),
//                    router.getPathParams());
//
//            server.createContext("/users/registration", exchange -> {
//                new RegistrationUserRoute(exchange, controller);
//            });
//
//            server.start();
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to start http server: ");
//        }
//    }
//
//    public void stop() {
//        server.stop(0);
//    }
//
//    public int getHttpPort() {
//        return server.getAddress().getPort();
//    }
}
