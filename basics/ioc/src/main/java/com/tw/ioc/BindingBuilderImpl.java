package com.tw.ioc;

import java.lang.annotation.Annotation;
import java.util.HashMap;

/**
 * Created by pzzheng on 12/20/16.
 */
public class BindingBuilderImpl<T> implements AnnotatedBindingBuilder<T> {
    private final Class<T> toInjectClass;
//    private final HashMap<Class<?>, Binding<?>> mutableBindings;
    private final HashMap<Key<?>, Binding<?>> mutableBindingsWithKey;

//    public BindingBuilderImpl(Class<T> toInjectClass, HashMap<Class<?>, Binding<?>> mutableBindings) {
//        this.toInjectClass = toInjectClass;
//        this.mutableBindings = mutableBindings;
////        this.mutableBindingsWithKey = null;
//    }

    public BindingBuilderImpl(Class<T> toInjectClass, HashMap<Key<?>, Binding<?>> mutableBindingsWithKey) {
        this.toInjectClass = toInjectClass;
        this.mutableBindingsWithKey = mutableBindingsWithKey;
    }

    @Override
    public ScopeBindingBuilder<T> to(Class<? extends T> toInjectImplClass) {
//        Binding<?> binding = mutableBindings.get(toInjectClass);
        Binding<?> binding = mutableBindingsWithKey.get(Key.of(toInjectClass));
        Provider<T> provider = Providers.fromImplementationClass(toInjectImplClass);
        provider = binding.getScope() == null ? provider : binding.getScope().scope(provider);
        mutableBindingsWithKey.replace(Key.of(toInjectClass), new Binding<>(toInjectClass, provider, binding.getAnnotationType(), binding.getScope()));
        return new ScopeBindingBuilderImpl<T>(toInjectClass, mutableBindingsWithKey);
    }

    @Override
    public void toInstance(T instance) {
        Binding<?> binding = mutableBindingsWithKey.get(Key.of(toInjectClass));
        mutableBindingsWithKey.replace(Key.of(toInjectClass), new Binding<T>(toInjectClass, Providers.fromInstance(instance), binding.getAnnotationType(), binding.getScope()));
    }

    @Override
    public LinkedBindingBuilder<T> annotatedWith(Annotation annotation) {
        Binding<?> binding = mutableBindingsWithKey.get(Key.of(toInjectClass));
        mutableBindingsWithKey.replace(Key.of(toInjectClass), new Binding(toInjectClass, binding.getProvider(), annotation.annotationType(), binding.getScope()));
        return new BindingBuilderImpl<T>(toInjectClass, mutableBindingsWithKey);
    }
}
