package web.http.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

//#todo: Реализовать polishing

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private int status;
    private Map<String, String> headers;
    private byte[] body;

    public static Response ok(byte[] body) {
        Response r = new Response();
        r.status = 200;
        r.body = body;
        return r;
    }

    public static Response created(byte[] body) {
        Response r = new Response();
        r.status = 201;
        r.body = body;
        return r;
    }

    public static Response notFound() {
        Response r = new Response();
        r.status = 404;
        return r;
    }

    public static Response badRequest() {
        Response r = new Response();
        r.status = 400;
        String message = "Bad Request";
        r.body = message.getBytes();
        return r;
    }

    public static Response internalError() {
        Response r = new Response();
        r.status = 500;
        String message = "Internal Server Error";
        r.body = message.getBytes();
        return r;
    }

    public void setHeaders(String key, String value) {
        headers.put(key, value);
    }
}
