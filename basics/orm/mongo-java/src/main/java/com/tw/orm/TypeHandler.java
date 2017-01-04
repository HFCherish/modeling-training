package com.tw.orm;

/**
 * Created by pzzheng on 12/28/16.
 */

import java.util.Optional;

/**
 * define the conversion between two class S & T
 * @param <S> the source class, which you want to map T to
 * @param <T> the target class, which you want to unmap S to
 */
public interface TypeHandler<S, T> {
//    boolean canConvert(ConversionType sourceType, ConversionType targetType);
    Optional<Converter> getConverter(ConversionType sourceType, ConversionType targetType);

    interface  Converter<S, T> {
        T convert(S sourceObj, ConversionContext conversionContext);
    }

}
