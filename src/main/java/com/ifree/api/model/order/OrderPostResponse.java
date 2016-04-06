package com.ifree.api.model.order;

import com.ifree.api.model.Meta;
import com.ifree.api.model.Model;
import com.ifree.api.model.ResponseCode;

import java.util.Random;

@ResponseCode(200)
@Meta(schema = "schemas/api/order/postResponse.json")
public class OrderPostResponse extends Model {
    public int order_id = Math.abs(new Random().nextInt() + 1);
}
