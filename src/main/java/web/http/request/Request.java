package web.http.request;

import com.sun.net.httpserver.Headers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Request {
    public String httpMethod;
    public String path;
    public Headers headers;
    public byte[] body;
}
