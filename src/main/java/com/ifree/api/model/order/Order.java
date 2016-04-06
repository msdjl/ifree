package com.ifree.api.model.order;

import com.ifree.api.model.Meta;
import com.ifree.api.model.Model;
import com.ifree.api.model.OptionalField;

import java.sql.Timestamp;
import java.util.Date;

@Meta(endpoint = "/api/order", schema = "schemas/api/order/post.json")
public class Order extends Model {
    public String name = "Фамилия Имя Отчество";
    public String phone = "+1234567890";
    public String region = "Пермский край";
    public String city = "Пермь";
    public String address = "Комсомольский проспект 1";
    public Timestamp clientTime = new Timestamp(new Date().getTime());
    public Integer delivery_id = 1;
    public Integer payment_method_id = 1;
    public Double delivery_price = 100.51;
    public Product[] products = new Product[] { new Product() };
    @OptionalField
    public String email = "mail@mail.ru";
    @OptionalField
    public String comment = "it's a comment string";
    @OptionalField
    public Integer postal_code = 123456;
    @OptionalField
    public String device_id = "just a random string";
}
