package com.tw.orm;

/**
 * Created by pzzheng on 1/4/17.
 */
public interface Converter<S, T> {
    T convert(S sourceObj, ConversionContext conversionContext);
}
