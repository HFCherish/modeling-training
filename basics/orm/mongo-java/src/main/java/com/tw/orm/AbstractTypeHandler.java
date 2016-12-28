package com.tw.orm;

/**
 * Created by pzzheng on 12/28/16.
 */
public abstract class AbstractTypeHandler<S, T> implements TypeHandler<S, T> {
    protected abstract Converter<S, T> encode(S sourceObj);
    protected abstract Converter<T, S> decode(T targetObj);

    @Override
    public boolean canConvert(Class<?> sourceClass, Class<?> targetClass) {
        return false;
    }

    @Override
    public Converter getConverter(Class<?> sourceClass, Class<?> targetClass) {
        return null;
    }
}
