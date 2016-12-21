package com.tw.ioc;

import java.lang.annotation.Annotation;

/**
 * Created by pzzheng on 12/20/16.
 */
public class Key<T> {
    private Class<T> toInjectClass;
    private Class<? extends Annotation> qualifierType;

    private Key(Class<T> toInjectClass, Class<? extends Annotation> qualifierType) {
        this.toInjectClass = toInjectClass;
        this.qualifierType = qualifierType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key<?> key = (Key<?>) o;

        if (!toInjectClass.equals(key.toInjectClass)) return false;
        return qualifierType != null ? qualifierType.equals(key.qualifierType) : key.qualifierType == null;

    }

    @Override
    public int hashCode() {
        int result = toInjectClass.hashCode();
        result = 31 * result + (qualifierType != null ? qualifierType.hashCode() : 0);
        return result;
    }

    public static <T> Key<T> of(Class<T> toInjectClass) {
        return new Key<T>(toInjectClass, null);
    }
}
