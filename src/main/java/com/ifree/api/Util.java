package com.ifree.api;


import com.ifree.api.model.Meta;
import com.jayway.restassured.module.jsv.JsonSchemaValidator;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Util {
    public final static String baseUrl = System.getProperty("baseUrl", "http://127.0.0.1:8080");

    public static boolean isValidJSON(String jsonString, Class<?> target) {
        Meta m = target.getAnnotation(Meta.class);
        boolean isValid;
        isValid = matchesJsonSchemaInClasspath(m.schema()).matches(jsonString);
        return isValid;
    }
    public static JsonSchemaValidator isAn (Class<?> target) {
        return isA(target);
    }
    public static JsonSchemaValidator isA (Class<?> target) {
        Meta m = target.getAnnotation(Meta.class);
        return matchesJsonSchemaInClasspath(m.schema());
    }
    public static List<Integer> getUnavailableOffers() {
        List<Integer> list = new ArrayList<>();
        list.add(404);
        list.add(404404);
        return list;
    }
}
