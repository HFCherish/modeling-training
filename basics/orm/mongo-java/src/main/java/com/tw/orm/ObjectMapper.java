package com.tw.orm;

import org.bson.Document;

/**
 * Created by pzzheng on 12/28/16.
 */
public interface ObjectMapper {
    <T> Document mapToDocument(T obj);
    <T> T mapToPojo(Document document, Class<T> pojoClass);
    void registerObjectDescriptor(ObjectDescriptor objectDescriptor);
}
