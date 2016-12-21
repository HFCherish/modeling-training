package com.tw.ioc;

import java.lang.annotation.Annotation;
import java.util.HashMap;

/**
 * Created by pzzheng on 12/20/16.
 */
public class BindingBuilderImpl<T> implements AnnotatedBindingBuilder<T> {
//    private final Class<T> toInjectClass;
    private final Key<T> toInjectKey;
    private final HashMap<Key<?>, Binding<?>> mutableBindings;

//    public BindingBuilderImpl(Class<T> toInjectClass, HashMap<Key<?>, Binding<?>> mutableBindings) {
//        this.toInjectClass = toInjectClass;
//        this.mutableBindings = mutableBindings;
//    }


    public BindingBuilderImpl(Key<T> toInjectKey, HashMap<Key<?>, Binding<?>> mutableBindings) {
        this.toInjectKey = toInjectKey;
        this.mutableBindings = mutableBindings;
    }

    @Override
    public ScopeBindingBuilder<T> to(Class<? extends T> toInjectImplClass) {
        Binding<?> binding = mutableBindings.get(toInjectKey);
        Provider<T> provider = Providers.fromImplementationClass(toInjectImplClass);
        provider = binding.getScope() == null ? provider : binding.getScope().scope(provider);
        mutableBindings.replace(toInjectKey, new Binding<>(toInjectKey.getToInjectClass(), provider, binding.getAnnotationType(), binding.getScope()));
        return new ScopeBindingBuilderImpl<T>(toInjectKey, mutableBindings);
    }

    @Override
    public void toInstance(T instance) {
        Binding<?> binding = mutableBindings.get(toInjectKey);
        mutableBindings.replace(toInjectKey, new Binding<T>(toInjectKey.getToInjectClass(), Providers.fromInstance(instance), binding.getAnnotationType(), binding.getScope()));
    }

    @Override
    public LinkedBindingBuilder<T> annotatedWith(Annotation annotation) {
        Binding<?> binding = mutableBindings.get(toInjectKey);
        mutableBindings.replace(toInjectKey, new Binding(toInjectKey.getToInjectClass(), binding.getProvider(), annotation.annotationType(), binding.getScope()));
        return new BindingBuilderImpl<T>(toInjectKey, mutableBindings);
    }
}
