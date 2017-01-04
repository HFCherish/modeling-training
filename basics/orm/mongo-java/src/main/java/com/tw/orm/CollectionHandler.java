package com.tw.orm;

import org.bson.Document;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by pzzheng on 1/3/17.
 */
public class CollectionHandler extends AbstractTypeHandler<Collection<Document>, Collection<Object>> {


    public CollectionHandler(Class<Collection<Document>> source, Class<Collection<Object>> target) {
        super(source, target);
    }

//    public CollectionHandler() {
//        super(Collection<Document>.class, Collection<Object>.class);
//    }

    @Override
    public Optional<Converter> getConverter(ConversionType sourceType, ConversionType targetType) {
        if (Collection.class.isAssignableFrom(sourceType.getClazz())
                && Collection.class.isAssignableFrom(targetType.getClazz())) {
            if (isTypeArgumentsEquals(sourceType, Document.class)) return Optional.of(map(sourceType, targetType));
            if (isTypeArgumentsEquals(targetType, Document.class)) return Optional.of(unmap(sourceType, targetType));
        }
        return Optional.empty();
    }

    @Override
    protected Converter<Collection<Document>, Collection<Object>> map(ConversionType sourceType, ConversionType targetType) {
        return (documents, conversionContext) -> {
            Collection<Object> res = ReflectionUtil.instanceOfCollection(targetType);
            documents.stream().forEach(doc -> {
                res.add(conversionContext.convert(doc, ConversionType.of((Class) targetType.getParameterizedType().getActualTypeArguments()[0])));
            });
            return res;
        };
    }

    @Override
    protected Converter<Collection<Object>, Collection<Document>> unmap(ConversionType sourceType, ConversionType targetType) {
        return null;
    }

    private boolean isTypeArgumentsEquals(ConversionType type, Class<?> clazz) {
        if (type.getParameterizedType() == null) return false;
        return type.getParameterizedType().getActualTypeArguments()[0].equals(clazz);
    }
}
