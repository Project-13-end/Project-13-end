package com.ll.project_13_backend.payment.entity;

import lombok.Getter;

@Getter
public enum Status {

    SUCCESS("success"),
    FAIL("fail");

    private Status(String value) {
        this.value = value;
    }
    private final String value;
}
