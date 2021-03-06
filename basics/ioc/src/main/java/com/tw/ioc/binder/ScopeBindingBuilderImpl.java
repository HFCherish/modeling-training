package com.tw.ioc.binder;

import com.tw.ioc.Scope;

import java.util.HashMap;

/**
 * Created by pzzheng on 12/20/16.
 */
public class ScopeBindingBuilderImpl<T> implements ScopeBindingBuilder<T> {
    private final Key<T> toInjectKey;
    private final HashMap<Key<?>, Binding<?>> mutableBindings;

    public ScopeBindingBuilderImpl(Key<T> toInjectKey, HashMap<Key<?>, Binding<?>> mutableBindings) {
        this.toInjectKey = toInjectKey;
        this.mutableBindings = mutableBindings;
    }

    @Override
    public void in(Scope scope) {
        Binding<?> binding = mutableBindings.get(toInjectKey);
        mutableBindings.replace(toInjectKey, new Binding(toInjectKey.getToInjectClass(), scope.scope(binding.getProvider()), binding.getAnnotationType(), scope));
    }
}
