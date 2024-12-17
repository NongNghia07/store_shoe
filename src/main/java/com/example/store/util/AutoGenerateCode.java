package com.example.store.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoGenerateCode {
    String prefix();  // Tiền tố cho mã code (ví dụ: "PRD" cho Product, "ORD" cho Order)
}