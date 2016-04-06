package com.ifree.api.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Meta {
    String endpoint() default "";
    String schema();
}
