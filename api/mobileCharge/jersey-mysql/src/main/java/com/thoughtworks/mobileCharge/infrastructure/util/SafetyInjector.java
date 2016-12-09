package com.thoughtworks.mobileCharge.infrastructure.util;

import com.google.inject.Injector;

import javax.inject.Inject;

/**
 * Created by pzzheng on 12/9/16.
 */
public class SafetyInjector {
    @Inject
    static Injector injector;

    public static <T> T injectMembers(T obj) {
        if (obj == null)    return null;
        injector.injectMembers(obj);
        return obj;
    }
}
