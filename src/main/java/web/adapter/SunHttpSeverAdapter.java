package web.adapter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import web.http.request.Request;
import web.http.response.Response;
import web.router.Router;

import java.io.IOException;
import java.util.Collections;

public class SunHttpSeverAdapter implements HttpHandler {

    private final Router router;

    public SunHttpSeverAdapter(Router router) {
        this.router = router;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Request request = toRequest(exchange);
        Response response = router.route(request);

        applyHeaders(exchange, response);

        byte[] body = response.getBody();
        exchange.sendResponseHeaders(
                response.getStatus(),
                body == null ? -1 : body.length
        );

        if (body != null) {
            exchange.getResponseBody().write(body);
        }

        exchange.getResponseBody().close();
    }

    private Request toRequest(HttpExchange exchange) throws IOException {
        Request request = new Request();
        request.body = exchange.getRequestBody().readAllBytes();
        request.httpMethod = exchange.getRequestMethod();
        request.path = exchange.getRequestURI().getPath();
        request.headers = exchange.getRequestHeaders();
        return request;
    }

    private void applyHeaders(HttpExchange exchange, Response response) {
        if (response.getHeaders() == null) return;

        response.getHeaders().forEach(
                (k, v) -> exchange.getResponseHeaders()
                        .put(k, Collections.singletonList(v))
        );
    }
}
