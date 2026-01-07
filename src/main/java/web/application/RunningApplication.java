package web.application;

import web.controller.controllers.BaseController;
import web.routing.SunHttpSeverAdapter;

public class RunningApplication implements Application {
    private final SunHttpSeverAdapter server;

    public RunningApplication(SunHttpSeverAdapter server) {
        this.server = server;
    }

    @Override
    public void start(int port, BaseController controller) {
        server.start(port, controller);
    }

    @Override
    public void stop() {
        server.stop();
    }
}
