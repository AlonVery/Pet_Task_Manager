package web.routing;

import com.sun.net.httpserver.HttpServer;
import web.controller.controllers.BaseController;
import web.response.RegistrationUserHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SunHttpSeverAdapter {
    HttpServer server;

    public void start(int port, BaseController controller) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);

            server.createContext("/users/registration", exchange -> {
                new RegistrationUserHandler(server, exchange, controller);
            });

            server.start();
        } catch (IOException e) {
            throw new RuntimeException("Failed to start http server: ");
        }
    }

    public void stop() {
        server.stop(0);
    }

    public int getHttpPort() {
        return server.getAddress().getPort();
    }
}
