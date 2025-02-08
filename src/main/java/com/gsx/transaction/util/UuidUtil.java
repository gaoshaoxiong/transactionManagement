package com.gsx.transaction.util;

import java.util.UUID;

public class UuidUtil {

    public static String generate() {
        return java.util.UUID.randomUUID().toString();
    }

    public static Boolean verify(String value) {
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}