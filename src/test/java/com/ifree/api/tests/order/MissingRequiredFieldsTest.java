package com.ifree.api.tests.order;

import com.ifree.api.tests.TestBase;
import com.ifree.api.model.errors.Error40;
import com.ifree.api.model.order.Order;
import com.ifree.api.model.order.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.ifree.api.Util.isAn;
import static com.jayway.restassured.RestAssured.given;

public class MissingRequiredFieldsTest extends TestBase {
    @DataProvider
    public Object[][] orderRequiredFields() {
        Order order = new Order();
        String[] fields = order.getRequiredFields();
        Object[][] result = new Object[fields.length][];
        for (int i = 0; i < fields.length; i++) {
            result[i] = new String[] {fields[i]};
        }
        return result;
    }

    @DataProvider
    public Object[][] productRequiredFields() {
        Product product = new Product();
        String[] fields = product.getRequiredFields();
        Object[][] result = new Object[fields.length][];
        for (int i = 0; i < fields.length; i++) {
            result[i] = new String[] {fields[i]};
        }
        return result;
    }

    @Test(description = "server should return common 400 error when we send invalid data ()", dataProvider = "orderRequiredFields")
    public void testError40SchemaWithoutOrderProperty(String field) throws Exception {
        Order order = new Order();
        JSONObject json = new JSONObject(order.toJSON());
        json.remove(field);

        given().body(json.toString()).when().post(order.getEndPoint()).then()
                .assertThat().body(isAn(Error40.class));
    }

    @Test(description = "server should return common 400 error when we send invalid data", dataProvider = "productRequiredFields")
    public void testError40SchemaWithoutProductProperty(String field) throws Exception {
        Order order = new Order();
        JSONObject json = new JSONObject(order.toJSON());
        JSONArray products = json.getJSONArray("products");
        JSONObject product = (JSONObject) products.get(0);
        product.remove(field);

        given().body(json.toString()).when().post(order.getEndPoint()).then()
                .assertThat().body(isAn(Error40.class));
    }

    @Test(description = "server should return common 400 error when we send invalid data")
    public void testError40ResponseCode() throws Exception {
        Error40 error = new Error40();
        Order order = new Order();
        JSONObject json = new JSONObject(order.toJSON());
        json.remove((String) orderRequiredFields()[0][0]);

        given().body(json.toString()).when().post(order.getEndPoint()).then()
                .assertThat().statusCode(error.getResponseCode());
    }
}
