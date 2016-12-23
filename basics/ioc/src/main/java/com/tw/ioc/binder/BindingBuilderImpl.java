package com.tw.ioc.binder;

import com.tw.ioc.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by pzzheng on 12/20/16.
 */
public class BindingBuilderImpl<T> implements AnnotatedBindingBuilder<T> {
    private final Key<T> toInjectKey;
    private final HashMap<Key<?>, Binding<?>> mutableBindings;

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
    public void toProvider(Class<? extends Provider> providerClass) {
        Binding<?> binding = mutableBindings.get(toInjectKey);
        try {
            Constructor<? extends Provider> emptyConstructor = providerClass.getDeclaredConstructor(new Class[0]);
            emptyConstructor.setAccessible(true);
            mutableBindings.replace(toInjectKey, new Binding<T>(toInjectKey.getToInjectClass(), emptyConstructor.newInstance(new Object[0]), binding.getAnnotationType(), binding.getScope()));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedBindingBuilder<T> annotatedWith(Annotation annotation) {
        Binding<?> binding = mutableBindings.get(toInjectKey);
        mutableBindings.remove(toInjectKey);
        Key<T> newInjectKey = Key.of(toInjectKey.getToInjectClass(), annotation);
        mutableBindings.put(newInjectKey, new Binding(toInjectKey.getToInjectClass(), binding.getProvider(), annotation.annotationType(), binding.getScope()));
        return new BindingBuilderImpl<T>(newInjectKey, mutableBindings);
    }

    @Override
    public LinkedBindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationClass) {
        Binding<?> binding = mutableBindings.get(toInjectKey);
        mutableBindings.remove(toInjectKey);
        Key<T> newInjectKey = Key.of(toInjectKey.getToInjectClass(), annotationClass);
        mutableBindings.put(newInjectKey, new Binding(toInjectKey.getToInjectClass(), binding.getProvider(), annotationClass, binding.getScope()));
        return new BindingBuilderImpl<T>(newInjectKey, mutableBindings);
    }
}
