package com.tw.orm;

/**
 * Created by pzzheng on 12/28/16.
 */
public class ConversionContext {

    <S, T> T convert(S sourceObj, Class<T> targetClass) {
        Class<?> sourceClass = sourceObj.getClass();
        if(sourceClass.equals(targetClass)) {
            return (T) sourceObj;
        }
        return null;
    }

    void registerTypeHandler(TypeHandler typeHandler) {

    }
}
