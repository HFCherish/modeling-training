package com.tw.ioc;

import java.lang.annotation.Annotation;

/**
 * Created by pzzheng on 12/20/16.
 */
public class Binding<T> {
    Class<T> toInjectClass;
    Class<? extends T> toInjectImplClass;
    Class<? extends Annotation> annotationType;
    Scope scope;

    public Binding(Class<T> toInjectClass, Class<? extends T> toInjectImplClass, Class<? extends Annotation> annotationType, Scope scope) {
        this.toInjectClass = toInjectClass;
        this.toInjectImplClass = toInjectImplClass;
        this.annotationType = annotationType;
        this.scope = scope;
    }

    public Class<T> getToInjectClass() {
        return toInjectClass;
    }

    public Class<? extends T> getToInjectImplClass() {
        return toInjectImplClass;
    }

    public Class<? extends Annotation> getAnnotationType() {
        return annotationType;
    }

    public Scope getScope() {
        return scope;
    }
}
