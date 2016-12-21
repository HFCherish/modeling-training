package com.tw.ioc.util;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by pzzheng on 12/21/16.
 */
public class AnnotationHelper {
    public static Optional<Annotation> findAnnotationByMetaAnnotationType(Annotation[] annotations, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(annotations).filter(annotation -> annotation.annotationType().isAnnotationPresent(annotationClass)).findFirst();
    }
}
