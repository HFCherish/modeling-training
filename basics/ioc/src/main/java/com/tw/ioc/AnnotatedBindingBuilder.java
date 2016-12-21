package com.tw.ioc;

import java.lang.annotation.Annotation;

/**
 * Created by pzzheng on 12/19/16.
 */
public interface AnnotatedBindingBuilder<T> extends LinkedBindingBuilder<T>{

    LinkedBindingBuilder<T> annotatedWith(Annotation annotation);
}
