package web.http.request;

import com.sun.net.httpserver.Headers;
import lombok.*;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Request {
    public String httpMethod;
    public String path;
    public Headers headers;
    public byte[] body;
    public Map<String, String> query;

    public Request(String httpMethod, String path, Headers headers, byte[] body) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.headers = headers;
        this.body = body;
    }

    public String getCleanPath() {
        int indexEndPoint = path.indexOf('?');
        if (indexEndPoint != -1) {
            return path.substring(0, indexEndPoint);
        }
        return path;
    }

    public Map<String, String> getQueryParams() {
        Map<String, String> params = new LinkedHashMap<>();
        int indexEndPoint = path.indexOf('?');

        if (indexEndPoint == -1 || indexEndPoint == path.length() - 1) {
            return params;
        }

        String query = path.substring(indexEndPoint + 1);
        for (String pair : query.split("&")) {
            String[] keyValue = pair.split("=");
            String key = keyValue[0];
            String value = keyValue[1].length() > 1 ? keyValue[1] : "";
            params.put(key, value);
        }
        return params;
    }

}
