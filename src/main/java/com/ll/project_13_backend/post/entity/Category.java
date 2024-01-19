package com.ll.project_13_backend.post.entity;

import com.ll.project_13_backend.global.exception.ErrorCode;
import com.ll.project_13_backend.global.exception.InvalidValueException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Category {

    KOR("kor"),
    ENG("eng"),
    MATH("math");

    Category(String value) {
        this.value = value;
    }

    public static Category match(String category) {
        return Arrays.stream(values())
                .filter(c -> c.value.equals(category))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE));
    }

    private final String value;
}
