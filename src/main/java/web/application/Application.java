package web.application;

import com.sun.net.httpserver.HttpServer;
import web.controller.controllers.BaseController;

public interface Application {

    void start(int port, BaseController controller);

    void stop();

}
