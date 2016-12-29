package com.tw.orm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by pzzheng on 12/28/16.
 */
public class ConversionContext {

    List<TypeHandler> typeHandlers = new ArrayList<>();

    <S,T> T convert(S sourceObj, Class<T> targetClass) {
        Class<?> sourceClass = sourceObj.getClass();
        if(sourceClass.equals(targetClass)) {
            return (T) sourceObj;
        }

        Optional<TypeHandler> typeHandler = typeHandlers.stream().filter(t -> t.canConvert(sourceClass, targetClass)).findFirst();
        if (!typeHandler.isPresent())
            throw new NoTypeHandlerException("no type handler from " + sourceClass + " to " + targetClass);

        return ((TypeHandler.Converter<S,T>)typeHandler.get().getConverter(sourceClass, targetClass)).convert(sourceObj);
    }

    void registerTypeHandler(TypeHandler typeHandler) {
        typeHandlers.add(typeHandler);
    }
}
