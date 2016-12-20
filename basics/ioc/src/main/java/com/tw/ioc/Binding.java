package com.tw.ioc;

import java.lang.annotation.Annotation;

/**
 * Created by pzzheng on 12/20/16.
 */
public class Binding<T> {
    Class<T> toInjectClass;
    Provider<T> provider;
    Class<? extends Annotation> annotationType;
    Scope scope;

    public Binding(Class<T> toInjectClass, Provider<T> provider, Class<? extends Annotation> annotationType, Scope scope) {
        this.toInjectClass = toInjectClass;
        this.provider = provider;
        this.annotationType = annotationType;
        this.scope = scope;
    }

    public Class<T> getToInjectClass() {
        return toInjectClass;
    }

    public Provider<T> getProvider() {
        return provider;
    }

    public Class<? extends Annotation> getAnnotationType() {
        return annotationType;
    }

    public Scope getScope() {
        return scope;
    }
}
