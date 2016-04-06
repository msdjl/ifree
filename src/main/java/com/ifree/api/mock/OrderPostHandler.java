package com.ifree.api.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifree.api.model.errors.Error10;
import com.ifree.api.model.errors.Error40;
import com.ifree.api.model.errors.Error50;
import com.ifree.api.model.order.Order;
import com.ifree.api.model.order.OrderPostResponse;
import com.ifree.api.model.order.Product;
import org.mockserver.mock.action.ExpectationCallback;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.util.ArrayList;
import java.util.List;

import static com.ifree.api.Util.getUnavailableOffers;
import static com.ifree.api.Util.isValidJSON;
import static org.mockserver.model.Header.header;
import static org.mockserver.model.HttpResponse.response;

public class OrderPostHandler implements ExpectationCallback {
    public static HttpResponse httpResponse = response()
            .withHeaders(
                    header("Content-Type", "application/json"),
                    header("Connection", "keep-alive")
            );

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        Error50 error50 = new Error50();
        int code = 0;
        String body = "";
        try {
            body = error50.toJSON();
            code = error50.getResponseCode();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            boolean isValid = isValidJSON(httpRequest.getBodyAsString(), Order.class);
            if (!isValid) {
                Error40 error40 = new Error40();
                code = error40.getResponseCode();
                body = error40.toJSON();
            } else {
                List<Integer> missedItems = new ArrayList<>();
                Order order = new ObjectMapper().readValue(httpRequest.getBodyAsString(), Order.class);
                for (Product product : order.products) {
                    if (getUnavailableOffers().contains(product.product)) {
                        missedItems.add(product.product);
                        System.out.println(product.product);
                    }
                }
                if (missedItems.size() > 0) {
                    Error10 error10 = new Error10(missedItems);
                    code = error10.getResponseCode();
                    body = error10.toJSON();
                } else {
                    OrderPostResponse orderPostResponse = new OrderPostResponse();
                    code = orderPostResponse.getResponseCode();
                    body = orderPostResponse.toJSON();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResponse.withBody(body).withStatusCode(code);
    }
}
