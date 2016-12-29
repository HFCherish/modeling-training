package com.tw.orm;

/**
 * Created by pzzheng on 12/28/16.
 */

/**
 * define the conversion between two class S & T
 * @param <S> the source class, which you want to map T to
 * @param <T> the target class, which you want to unmap S to
 */
public interface TypeHandler<S, T> {
    boolean canConvert(Class<?> sourceClass, Class<?> targetClass);
    Converter getConverter(Class<?> sourceClass, Class<?> targetClass);

    interface  Converter<S, T> {
        T convert(S sourceObj);
    }

}
