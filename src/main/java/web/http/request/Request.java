package web.http.request;

import com.sun.net.httpserver.Headers;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Request {
    public String httpMethod;
    public String path;
    public Headers headers;
    public byte[] body;
}
