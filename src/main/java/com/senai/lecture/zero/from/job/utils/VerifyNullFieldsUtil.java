package com.senai.lecture.zero.from.job.utils;

import java.util.function.Consumer;

public class VerifyNullFieldsUtil {

    public static <T> void updateIfPresent(Consumer<T> consumer, T value) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    private VerifyNullFieldsUtil() {}

}
