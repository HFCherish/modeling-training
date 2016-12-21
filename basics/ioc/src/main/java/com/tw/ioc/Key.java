package com.tw.ioc;

import java.lang.annotation.Annotation;

/**
 * Created by pzzheng on 12/20/16.
 */
public class Key<T> {
    private Class<T> toInjectClass;
    private Class<? extends Annotation> qualifierType;
    private Annotation qualifier;

//    private Key(Class<T> toInjectClass, Class<? extends Annotation> qualifierType) {
//        this.toInjectClass = toInjectClass;
//        this.qualifierType = qualifierType;
//    }


    private Key(Class<T> toInjectClass, Class<? extends Annotation> qualifierType, Annotation qualifier) {
        this.toInjectClass = toInjectClass;
        this.qualifierType = qualifierType;
        this.qualifier = qualifier;
    }

    public Class<T> getToInjectClass() {
        return toInjectClass;
    }

    public Class<? extends Annotation> getQualifierType() {
        return qualifierType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key<?> key = (Key<?>) o;

        if (!toInjectClass.equals(key.toInjectClass)) return false;
        if (qualifierType != null ? !qualifierType.equals(key.qualifierType) : key.qualifierType != null) return false;
        return qualifier != null ? qualifier.equals(key.qualifier) : key.qualifier == null;

    }

    @Override
    public int hashCode() {
        int result = toInjectClass.hashCode();
        result = 31 * result + (qualifierType != null ? qualifierType.hashCode() : 0);
        result = 31 * result + (qualifier != null ? qualifier.hashCode() : 0);
        return result;
    }

    public static <T> Key<T> of(Class<T> toInjectClass) {
        return new Key<T>(toInjectClass, null, null);
    }

    public static <T> Key<T> of(Class<T> toInjectClass, Class<? extends Annotation> qualifierType) {
        return new Key<T>(toInjectClass, qualifierType, null);
    }

    public static <T> Key<T> of(Class<T> toInjectClass, Annotation qualifier) {
        return qualifier == null ? of(toInjectClass) : new Key<T>(toInjectClass, qualifier.annotationType(), qualifier);
    }
}
