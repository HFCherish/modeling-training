package com.tw.orm;

/**
 * Created by pzzheng on 12/28/16.
 */

/**
 * define the conversion between two class S & T
 * @param <S> the source class, which you want to map T to
 * @param <T> the target class, which you want to unmap S to
 */
public interface BiTypeHandler<S, T> extends TypeHandler {
    Converter<S, T> map(ConversionType sourceType, ConversionType targetType);
    Converter<T, S> unmap(ConversionType sourceType, ConversionType targetType);
}
