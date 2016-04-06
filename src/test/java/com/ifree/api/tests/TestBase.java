package com.ifree.api.tests;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpCallback;
import org.testng.annotations.BeforeSuite;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;

public abstract class TestBase {
    @BeforeSuite
    public void beforeSuite() throws Exception {
        ClientAndServer mockServer = startClientAndServer(8080);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                mockServer.stop();
            }
        });

        mockServer
                .when(request().withPath("/api/order").withMethod("POST"))
                .callback(HttpCallback.callback().withCallbackClass("com.ifree.api.mock.OrderPostHandler"));

    }
}
