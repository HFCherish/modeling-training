package com.tw.ioc.binder;

import com.tw.ioc.Scope;

import java.lang.annotation.Annotation;

/**
 * Created by pzzheng on 12/19/16.
 */
public interface Binder {
    <T> AnnotatedBindingBuilder<T> bind(Class<T> toInjectClass);

    <T> Binding<T> getBinding(Class<T> toInjectClass);

    <T> Binding<T> getBinding(Key<T> key);

    void bindScope(Class<? extends Annotation> scopeAnnotation, Scope scope);
}
