package com.senai.lecture.zero.from.job.utils;

import java.util.function.Consumer;

public class VerifyNullFieldsUtils {

    public static <T> void updateIfPresent(Consumer<T> consumer, T value) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    private VerifyNullFieldsUtils() {}

}
