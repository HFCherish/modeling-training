package com.tw.ioc;

import java.lang.annotation.Annotation;

/**
 * Created by pzzheng on 12/20/16.
 */
public class ScopeBinding {
    private final Class<? extends Annotation> scopeAnnotation;
    private final Scope scope;

    public ScopeBinding(Class<? extends Annotation> scopeAnnotation, Scope scope) {
        this.scopeAnnotation = scopeAnnotation;
        this.scope = scope;
    }

    public Class<? extends Annotation> getScopeAnnotation() {
        return scopeAnnotation;
    }

    public Scope getScope() {
        return scope;
    }
}
