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

    protected abstract Converter<S, T> map(ConversionType sourceType, ConversionType targetType);
    protected abstract Converter<T, S> unmap(ConversionType sourceType, ConversionType targetType);

//    @Override
//    public Optional<Converter> getConverter(ConversionType sourceType, ConversionType targetType) {
//        if (source.isAssignableFrom(sourceType.getClazz()) && target.isAssignableFrom(targetType.getClazz()))
//            return Optional.of(map(sourceType, targetType));
//        if (target.isAssignableFrom(sourceType.getClazz()) && source.isAssignableFrom(targetType.getClazz()))
//            return Optional.of(unmap(sourceType, targetType));
//        return Optional.empty();
//    }
}
