package com.ifree.api.model.errors;

import com.ifree.api.model.Meta;
import com.ifree.api.model.ResponseCode;

@ResponseCode(400)
@Meta(schema = "schemas/api/errorCode40.json")
public class Error40 extends Error {
    public Error40() {
        super("Invalid request",40);
    }
}
