package com.tw.ioc;

/**
 * Created by pzzheng on 12/20/16.
 */
public interface LinkedBindingBuilder<T> {
    ScopeBindingBuilder to(Class<? extends T> toInjectImplClass);

    void toInstance(T instance);
}
