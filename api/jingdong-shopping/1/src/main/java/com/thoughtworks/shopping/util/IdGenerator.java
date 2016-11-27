package com.thoughtworks.shopping.util;

import java.util.UUID;

/**
 * Created by pzzheng on 11/22/16.
 */
public class IdGenerator {
    public static String next() {
        return UUID.randomUUID().toString();
    }
}
