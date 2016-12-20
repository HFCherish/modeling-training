package com.tw.ioc;

import java.util.HashMap;

/**
 * Created by pzzheng on 12/20/16.
 */
public class ScopeBindingBuilderImpl<T> implements ScopeBindingBuilder<T> {
    private final Class<T> toInjectClass;
    private final HashMap<Class<?>, Binding<?>> mutableBindings;

    public ScopeBindingBuilderImpl(Class<T> toInjectClass, HashMap<Class<?>, Binding<?>> mutableBindings) {
        this.toInjectClass = toInjectClass;
        this.mutableBindings = mutableBindings;
    }

    @Override
    public void in(Scope scope) {
        Binding<?> binding = mutableBindings.get(toInjectClass);
        mutableBindings.replace(toInjectClass, new Binding(toInjectClass, scope.scope(binding.getProvider()), binding.getAnnotationType(), scope));
    }
}
