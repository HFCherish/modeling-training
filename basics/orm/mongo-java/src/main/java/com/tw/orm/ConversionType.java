package com.tw.orm;

import java.lang.reflect.ParameterizedType;

/**
 * Created by pzzheng on 1/4/17.
 */
public class ConversionType {
    private Class<?> clazz;
    private ParameterizedType parameterizedType;

    public static ConversionType of(Class<?> clazz) {
        ConversionType conversionType = new ConversionType();
        conversionType.clazz = clazz;
        return conversionType;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public ParameterizedType getParameterizedType() {
        return parameterizedType;
    }

    public String getName() {
        return parameterizedType == null ? clazz.getName() : parameterizedType.getTypeName();
    }

}
