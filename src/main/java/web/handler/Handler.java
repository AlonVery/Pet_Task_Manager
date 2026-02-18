package web.handler;

import web.http.request.Request;
import web.http.response.Response;

public interface Handler {
    Response handle(Request request);
}
