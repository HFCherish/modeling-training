package com.tw.ioc;

import java.util.HashMap;

/**
 * Created by pzzheng on 12/20/16.
 */
public class BindingBuilderImpl<T> implements AnnotatedBindingBuilder<T> {
    private final Class<T> toInjectClass;
    private final HashMap<Class<?>, Binding<?>> mutableBindings;

    public BindingBuilderImpl(Class<T> toInjectClass, HashMap<Class<?>, Binding<?>> mutableBindings) {
        this.toInjectClass = toInjectClass;
        this.mutableBindings = mutableBindings;
    }

    @Override
    public ScopeBindingBuilder<T> to(Class<? extends T> toInjectImplClass) {
        Binding<?> binding = mutableBindings.get(toInjectClass);
        mutableBindings.replace(toInjectClass, new Binding<>(toInjectClass, Providers.fromImplementationClass(toInjectImplClass), binding.getAnnotationType(), binding.getScope()));
        return new ScopeBindingBuilderImpl<T>(toInjectClass, mutableBindings);
    }

    @Override
    public void toInstance(T instance) {
        Binding<?> binding = mutableBindings.get(toInjectClass);
        mutableBindings.replace(toInjectClass, new Binding<T>(toInjectClass, Providers.fromInstance(instance), binding.getAnnotationType(), binding.getScope()));
    }
}
