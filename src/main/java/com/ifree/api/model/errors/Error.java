package com.ifree.api.model.errors;

import com.ifree.api.model.Model;

public abstract class Error extends Model{
    public String message;
    public int error_code;

    public Error(String message, int error_code) {
        this.message = message;
        this.error_code = error_code;
    }
}
