package com.ifree.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.ifree.api.Util.baseUrl;
import static com.jayway.restassured.RestAssured.given;

public class Model {
    public String toJSON() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
    public Response post() {
        return given().body(this).header("Content-Type", "application/json; charset=utf-8").when().post(this.getEndPoint());
    }
    @JsonIgnore
    public String getEndPoint() {
        Meta m = this.getClass().getAnnotation(Meta.class);
        return baseUrl + m.endpoint();
    }
    @JsonIgnore
    public String[] getRequiredFields() {
        return getFields(true);
    }
    @JsonIgnore
    public String[] getOptionalFields() {
        return getFields(false);
    }
    private String[] getFields(boolean required) {
        List<String> list = new ArrayList<>();
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(OptionalField.class) != required) {
                list.add(field.getName());
            }
        }
        return list.toArray(new String[0]);
    }
    @JsonIgnore
    public int getResponseCode() {
        ResponseCode rc = this.getClass().getAnnotation(ResponseCode.class);
        return rc.value();
    }
    public void nullify(String field) throws Exception{
        this.getClass().getField(field).set(this, null);
    }
}
