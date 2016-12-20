package com.tw.ioc;

/**
 * Created by pzzheng on 12/20/16.
 */
public final class Providers {
    private Providers() {}


    public static <T> Provider<T> froImplementationClass(Class<? extends T> toInjectImplClass) {
        return () -> {
            try {
                return toInjectImplClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        };
    }
}
