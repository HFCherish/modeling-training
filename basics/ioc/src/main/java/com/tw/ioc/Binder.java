package com.tw.ioc;

/**
 * Created by pzzheng on 12/19/16.
 */
public interface Binder {
    <T> AnnotatedBindingBuilder<T> bind(Class<T> toInjectClass);

    <T> Binding<T> getBinding(Class<T> toInjectClass);
}
