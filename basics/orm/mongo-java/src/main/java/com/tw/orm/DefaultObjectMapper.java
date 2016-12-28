package com.tw.orm;

import org.bson.Document;

/**
 * Created by pzzheng on 12/28/16.
 */
public class DefaultObjectMapper implements ObjectMapper {
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

    }
}
