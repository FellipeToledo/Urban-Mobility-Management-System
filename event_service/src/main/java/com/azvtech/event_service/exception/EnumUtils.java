package com.azvtech.event_service.exception;

import java.util.Arrays;

public class EnumUtils {
    public static String[] getNames(Enum<?>[] values) {
        return Arrays.stream(values)
                .map(Enum::name)
                .toArray(String[]::new);
    }
}
