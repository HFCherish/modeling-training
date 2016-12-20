package com.tw.ioc;

/**
 * Created by pzzheng on 12/20/16.
 */
public interface Scope {
    <T> Provider<T> scope(Provider<T> unScopedProvider);
}
