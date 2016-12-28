package com.tw.orm;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pzzheng on 12/28/16.
 */
public abstract class AbstractObjectMapper implements ObjectMapper {
    Map<Class<?>, ObjectDescriptor> objectDescriptors = new HashMap<>();

    @Override
    public <T> Document mapToDocument(T obj) {
        return null;
    }

    @Override
    public <T> T mapToPojo(Document document, Class<T> pojoClass) {
        return null;
    }

    @Override
    public void registerObjectDescriptor(ObjectDescriptor objectDescriptor) {
        objectDescriptors.put(objectDescriptor.getType(), objectDescriptor);
    }

    @Override
    public ObjectDescriptor getDescriptor(Class<?> type) {
        return objectDescriptors.get(type);
    }
}
