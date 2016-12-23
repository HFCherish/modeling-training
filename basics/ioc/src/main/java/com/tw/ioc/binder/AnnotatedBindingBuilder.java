package com.tw.ioc.binder;

import java.lang.annotation.Annotation;

/**
 * Created by pzzheng on 12/19/16.
 */
public interface AnnotatedBindingBuilder<T> extends LinkedBindingBuilder<T> {

    LinkedBindingBuilder<T> annotatedWith(Annotation annotation);

    LinkedBindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationClass);
}
