package com.ifree.api.model.errors;

import com.ifree.api.model.Meta;
import com.ifree.api.model.ResponseCode;

import java.util.Arrays;
import java.util.List;

@ResponseCode(400)
@Meta(schema = "schemas/api/order/errorCode10.json")
public class Error10 extends Error {
    public Error10 (int[] offers) {
        this(Arrays.asList(Arrays.stream(offers).boxed().toArray(Integer[]::new)));
    }
    public Error10 (List<Integer> offers) {
        super("",10);
        this.message = format(offers);
    }
    public Error10 (int offer) {
        this(new int[] {offer});
    }
    public Error10 () {
        this(0);
    }
    private String format(List<Integer> offers) {
        String format = "Unable to create Order: offer(s) %s is unavailable";
        return String.format(format, offers);
    }
}
