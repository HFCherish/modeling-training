package com.thoughtworks.ketsu.util;

import java.util.UUID;

/**
 * Created by pzzheng on 11/29/16.
 */
public class IdGenerator {
    public static String next() {
        return UUID.randomUUID().toString();
    }
}
