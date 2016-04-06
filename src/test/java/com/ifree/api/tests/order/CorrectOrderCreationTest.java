package com.ifree.api.tests.order;

import com.ifree.api.tests.TestBase;
import com.ifree.api.model.order.Order;
import com.ifree.api.model.order.OrderPostResponse;
import com.jayway.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import static com.ifree.api.Util.isA;
import static com.jayway.restassured.RestAssured.given;

public class CorrectOrderCreationTest extends TestBase {
    private String field;
    private Response response;

    public CorrectOrderCreationTest(String field) {
        this.field = field;
    }

    @BeforeClass
    public void beforeClass() throws Exception {
        Order order = new Order();
        JSONObject json = new JSONObject(order.toJSON());
        json.remove(field);
        response = given().body(json.toString()).when().post(order.getEndPoint());
    }

    @Test(description = "server should response with correct json schema when data is correct")
    public void testSchema() throws Exception {
        response.then()
                .assertThat().body(isA(OrderPostResponse.class));
    }

    @Test(description = "response should be with response code 200")
    public void testResponseCode() throws Exception {
        OrderPostResponse resp = new OrderPostResponse();
        response.then().assertThat().statusCode(resp.getResponseCode());
    }

    @Factory
    public static Object[] factory() {
        String[] fields = new Order().getOptionalFields();
        Object[] result = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            result[i] = new CorrectOrderCreationTest(fields[i]);
        }
        return result;
    }
}
