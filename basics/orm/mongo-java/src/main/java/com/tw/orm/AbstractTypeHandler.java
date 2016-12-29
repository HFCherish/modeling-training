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

    protected abstract Converter<S, T> map(Class<?> sourceClass, Class<?> targetClass);
    protected abstract Converter<T, S> unmap(Class<?> sourceClass, Class<?> targetClass);
//
//    @Override
//    public boolean canConvert(Class<?> sourceClass, Class<?> targetClass) {
//        if (source.equals(sourceClass))
//        return false;
//    }

    @Override
    public Converter getConverter(Class<?> sourceClass, Class<?> targetClass) {
//        System.out.println(source.getName());
//        System.out.println(target.getName());
        if(source.isAssignableFrom(sourceClass) && target.isAssignableFrom(targetClass))
            return map(sourceClass, targetClass);
        if(target.isAssignableFrom(sourceClass) && source.isAssignableFrom(targetClass))
            return unmap(sourceClass, targetClass);
        return null;
    }
}
