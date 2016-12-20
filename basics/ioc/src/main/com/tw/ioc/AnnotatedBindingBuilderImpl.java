package com.tw.ioc;

import java.util.HashMap;

/**
 * Created by pzzheng on 12/20/16.
 */
public class AnnotatedBindingBuilderImpl<T> implements AnnotatedBindingBuilder<T> {
    private final Class<T> toInjectClass;
    private final HashMap<Class<?>, Binding<?>> mutableBindings;

    public AnnotatedBindingBuilderImpl(Class<T> toInjectClass, HashMap<Class<?>, Binding<?>> mutableBindings) {
        this.toInjectClass = toInjectClass;
        this.mutableBindings = mutableBindings;
    }

    @Override
    public void to(Class<? extends T> toInjectImplClass) {
        Binding<?> binding = mutableBindings.get(toInjectClass);
        mutableBindings.replace(toInjectClass, new Binding(toInjectClass, toInjectImplClass, binding.getAnnotationType(), binding.getScope()));
    }
}
