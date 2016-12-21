package com.tw.ioc;

import java.util.HashMap;

/**
 * Created by pzzheng on 12/20/16.
 */
public class ScopeBindingBuilderImpl<T> implements ScopeBindingBuilder<T> {
    private final Class<T> toInjectClass;
//    private final HashMap<Class<?>, Binding<?>> mutableBindings;
    private final HashMap<Key<?>, Binding<?>> mutableBindings;

    public ScopeBindingBuilderImpl(Class<T> toInjectClass, HashMap<Key<?>, Binding<?>> mutableBindings) {
        this.toInjectClass = toInjectClass;
        this.mutableBindings = mutableBindings;
    }

    @Override
    public void in(Scope scope) {
        Binding<?> binding = mutableBindings.get(Key.of(toInjectClass));
        mutableBindings.replace(Key.of(toInjectClass), new Binding(toInjectClass, scope.scope(binding.getProvider()), binding.getAnnotationType(), scope));
    }
}
