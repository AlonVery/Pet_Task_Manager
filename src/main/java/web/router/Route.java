package web.router;

import web.handler.Handler;
import web.http.request.Request;

public record Route(String method, String path, Handler handler) {
    public boolean matches(Request request){
        if(request.httpMethod.equals(method)){
            return request.path.equals(path);
        }
        return false;
    }

    public void fillPathParams(Request request) {
        // тут реализовать логику парса из апи параметров в значения - "/qwe/{id} -> "id" =  1"
    }
}
