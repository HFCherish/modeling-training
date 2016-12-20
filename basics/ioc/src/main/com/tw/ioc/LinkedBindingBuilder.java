package com.tw.ioc;

/**
 * Created by pzzheng on 12/20/16.
 */
public interface LinkedBindingBuilder<T> {
    void to(Class<? extends T> toInjectImplClass);
}
