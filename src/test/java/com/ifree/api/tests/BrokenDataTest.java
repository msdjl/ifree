package com.ifree.api.tests;

import com.ifree.api.model.errors.Error50;
import com.ifree.api.model.order.Order;
import org.testng.annotations.Test;

import static com.ifree.api.Util.isAn;
import static com.jayway.restassured.RestAssured.given;

/**
 * Trying to send broken json body. Actually I don't know, maybe server should return common 400 error, but for the test let it be 500
 */
public class BrokenDataTest extends TestBase {
    @Test
    public void testError500Schema() throws Exception {
        given().body("{").when().post(new Order().getEndPoint()).then()
                .assertThat().body(isAn(Error50.class));
    }

    @Test
    public void testError500ResponseCode() throws Exception {
        Error50 error = new Error50();
        given().body("{").when().post(new Order().getEndPoint()).then()
                .assertThat().statusCode(error.getResponseCode());
    }
}
