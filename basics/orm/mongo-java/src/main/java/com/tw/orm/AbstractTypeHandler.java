package com.tw.orm;

/**
 * Created by pzzheng on 12/28/16.
 */
public abstract class AbstractTypeHandler<S, T> implements TypeHandler<S, T> {
    final Class<S> source;
    final Class<T> target;

    public AbstractTypeHandler(Class<S> source, Class<T> target) {
        this.source = source;
        this.target = target;
    }

    protected abstract Converter<S, T> map(Class<?> sourceClass, Class<?> targetClass, ConversionContext conversionContext);

    protected abstract Converter<T, S> unmap(Class<?> sourceClass, Class<?> targetClass, ConversionContext conversionContext);

    @Override
    public Converter getConverter(Class<?> sourceClass, Class<?> targetClass, ConversionContext conversionContext) {
        if (source.isAssignableFrom(sourceClass) && target.isAssignableFrom(targetClass))
            return map(sourceClass, targetClass, conversionContext);
        if (target.isAssignableFrom(sourceClass) && source.isAssignableFrom(targetClass))
            return unmap(sourceClass, targetClass, conversionContext);
        return null;
    }
}
