package com.ifree.api.tests.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifree.api.tests.TestBase;
import com.ifree.api.model.errors.Error10;
import com.ifree.api.model.order.Order;
import com.ifree.api.model.order.Product;
import com.jayway.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ifree.api.Util.getUnavailableOffers;
import static com.ifree.api.Util.isAn;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MissingOffersTest extends TestBase {
    private List<Integer> offers;
    private Response response;

    public MissingOffersTest(List<Integer> offers) {
        this.offers = offers;
    }

    @BeforeClass
    public void beforeClass() throws Exception {
        Product[] products = new Product[offers.size()];
        for (int i = 0; i < offers.size(); i++) {
            Product product = new Product();
            product.product = offers.get(i);
            products[i] = product;
        }
        Order order = new Order();
        order.products = products;

        response = order.post();
    }

    @Test(description = "response should be with correct schema")
    public void testError10Schema() throws Exception {
        response.then().assertThat().body(isAn(Error10.class));
    }

    @Test(description = "response should be with response code 400")
    public void testError10ResponseCode() throws Exception {
        Error10 error = new Error10();
        response.then().assertThat().statusCode(error.getResponseCode());
    }

    @Test(description = "response should be with correct list of missed offers")
    public void testError10OffersList() throws Exception {
        List<Integer> list = offers.stream().filter(offer -> getUnavailableOffers().contains(offer)).collect(Collectors.toList());
        Error10 expected = new Error10(list);
        Error10 actual = new ObjectMapper().readValue(response.thenReturn().body().asString(), Error10.class);
        assertThat(actual.message, equalTo(expected.message)); //TODO: need to compare only offers list instead of full message text
    }

    @Factory
    public static Object[] factory() {
        //TODO: looks creepy, need to refactor
        List<Integer> first = new ArrayList<>();
        first.add(getUnavailableOffers().get(0));
        List<Integer> second = new ArrayList<>();
        second.addAll(getUnavailableOffers());
        List<Integer> third = new ArrayList<>();
        third.add(getUnavailableOffers().get(0));
        third.add(1234567890);
        return new Object[] {
                new MissingOffersTest(first), new MissingOffersTest(second), new MissingOffersTest(third)
        };
    }
}
