package bd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import web.http.request.Request;

public class UsersFilterTests {

    @Test
    public void testCleanPathMethod() {
        Request request = new Request(
                "423reer",
                "/api/v1/orders?query=iphone&status=PAID&minPrice=1000&maxPrice=5000&limit=20&offset=40",
                null,
                null);
        
        //System.out.println(request.getCleanPath());
        Assertions.assertTrue(request.getCleanPath().startsWith("/api/v1/orders"));
    }

    @Test
    public void testGetQueryParamsFromRequest() {
        Request request = new Request(
                "423reer",
                "/api/v1/orders?query=iphone&status=PAID&minPrice=1000&maxPrice=5000&limit=20&offset=40",
                null,
                null);

        //System.out.println(request.getQueryParams());
        Assertions.assertEquals("iphone", request.getQueryParams().get("query"));
        Assertions.assertEquals("PAID", request.getQueryParams().get("status"));
        Assertions.assertEquals("1000", request.getQueryParams().get("minPrice"));
        Assertions.assertEquals("5000", request.getQueryParams().get("maxPrice"));
        Assertions.assertEquals("20", request.getQueryParams().get("limit"));
        Assertions.assertEquals("40", request.getQueryParams().get("offset"));
    }
}
