package com.tw.ioc.binder;

import com.tw.ioc.Scope;
import com.tw.ioc.util.AnnotationHelper;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by pzzheng on 12/20/16.
 */
public class RecordBinder implements Binder {
    private final HashMap<Key<?>, Binding<?>> mutableBindingsWithKey;
    private final HashMap<Class<? extends Annotation>, ScopeBinding> scopeBindings;

    public RecordBinder() {
        this.mutableBindingsWithKey = new HashMap<>();
        this.scopeBindings = new HashMap();
    }

    @Override
    public <T> AnnotatedBindingBuilder<T> bind(Class<T> toInjectClass) {
        Binding<T> binding = new Binding<>(toInjectClass, null, null, getScope(toInjectClass));
        mutableBindingsWithKey.put(Key.of(toInjectClass), binding);
        return new BindingBuilderImpl(Key.of(toInjectClass), mutableBindingsWithKey);
    }

    private <T> Scope getScope(Class<T> toInjectClass) {
        Annotation[] annotations = toInjectClass.getAnnotations();
        Optional<Annotation> scopeAnnotation = AnnotationHelper.findAnnotationByMetaAnnotationType(annotations, javax.inject.Scope.class);
        Scope scope = null;
        if(scopeAnnotation.isPresent()) {
            scope = scopeBindings.get(scopeAnnotation.get().annotationType()).getScope();
        }
        return scope;
    }

    @Override
    public <T> Binding<T> getBinding(Class<T> toInjectClass) {
        return getBinding(Key.of(toInjectClass));
    }

    @Override
    public <T> Binding<T> getBinding(Key<T> key) {
        return (Binding<T>) mutableBindingsWithKey.get(key);
    }

    @Override
    public void bindScope(Class<? extends Annotation> scopeAnnotation, Scope scope) {
        scopeBindings.put(scopeAnnotation, new ScopeBinding(scopeAnnotation, scope));
    }
}
