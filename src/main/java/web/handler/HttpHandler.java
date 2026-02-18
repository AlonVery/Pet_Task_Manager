package web.handler;

import com.sun.net.httpserver.HttpExchange;

@FunctionalInterface
public interface HttpHandler {
    void handle(HttpExchange httpExchange);
}
