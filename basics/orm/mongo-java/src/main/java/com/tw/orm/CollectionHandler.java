package com.tw.orm;

import org.bson.BsonArray;

import java.util.Collection;

/**
 * Created by pzzheng on 1/3/17.
 */
public class CollectionHandler extends AbstractTypeHandler<BsonArray, Collection> {
    public CollectionHandler() {
        super(BsonArray.class, Collection.class);
    }

    @Override
    public boolean canConvert(Class sourceClass, Class targetClass) {
        return (Collection.class.isAssignableFrom(sourceClass) && targetClass.equals(BsonArray.class))
                || (Collection.class.isAssignableFrom(targetClass) && sourceClass.equals(BsonArray.class));
    }

    @Override
    protected Converter<BsonArray, Collection> map(Class<?> sourceClass, Class<?> targetClass, ConversionContext conversionContext) {
        return null;
    }

    @Override
    protected Converter<Collection, BsonArray> unmap(Class<?> sourceClass, Class<?> targetClass, ConversionContext conversionContext) {
        return collection -> {

            return null;
        };
    }
}
