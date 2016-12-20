package com.tw.ioc;

import java.util.HashMap;

/**
 * Created by pzzheng on 12/20/16.
 */
public class RecordBinder implements Binder {
    private final HashMap<Class<?>, Binding<?>> mutableBindings;
//    private HashMap<Class<?>, Binding<?>> immutableBindings = Collections.unmodifiableMap(mutableBindings);

    public RecordBinder() {
        this.mutableBindings = new HashMap<>();
    }

    @Override
    public <T> AnnotatedBindingBuilder<T> bind(Class<T> toInjectClass) {
        Binding<T> binding = new Binding<>(toInjectClass, null, null, null);
        mutableBindings.put(toInjectClass, binding);
        return new BindingBuilderImpl(toInjectClass, mutableBindings);
    }

    @Override
    public <T> Binding<T> getBinding(Class<T> toInjectClass) {
        return (Binding<T>) mutableBindings.get(toInjectClass);
    }
}
