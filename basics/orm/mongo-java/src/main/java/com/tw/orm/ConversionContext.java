package com.tw.orm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by pzzheng on 12/28/16.
 */
public class ConversionContext {

    List<TypeHandler> typeHandlers = new ArrayList<>();

    <S, T> T convert(S sourceObj, ConversionType targetType) {
        if (sourceObj == null) return null;
        Class<?> sourceClass = sourceObj.getClass();
        if (targetType.getClazz().isAssignableFrom(sourceClass)) {
            return (T) sourceObj;
        }

        Optional<TypeHandler.Converter<Object, Object>> converter = getConverter(ConversionType.of(sourceClass), targetType);
        if (!converter.isPresent()) return null;
        return (T) converter.get().convert(sourceObj, this);
    }

    void registerTypeHandler(TypeHandler typeHandler) {
        typeHandlers.add(typeHandler);
    }


    private <S, T> Optional<TypeHandler.Converter<S, T>> getConverter(ConversionType sourceType, ConversionType targetType) {
        Optional<TypeHandler> typeHandler = typeHandlers.stream().filter(t -> t.getConverter(sourceType, targetType).isPresent()).findFirst();
        if (!typeHandler.isPresent())
            throw new NoTypeHandlerException("no type handler from " + sourceType.getName() + " to " + targetType.getName());

        return typeHandler.get().getConverter(sourceType, targetType);
    }
}
