package com.tw.ioc;

/**
 * Created by pzzheng on 12/19/16.
 */
public interface Injector {
    <T> T getInstance(Class<T> toInjectClass);

    <T> T getInstance(Key<T> key);

    void injectMembers(Object instance);
}
