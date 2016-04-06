package com.ifree.api.model.errors;

import com.ifree.api.model.Meta;
import com.ifree.api.model.ResponseCode;

@ResponseCode(500)
@Meta(schema = "schemas/api/errorCode50.json")
public class Error50 extends Error {
    public Error50() {
        super("Internal server error",50);
    }
}