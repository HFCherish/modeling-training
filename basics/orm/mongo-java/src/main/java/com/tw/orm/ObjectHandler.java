package com.tw.orm;

import org.bson.Document;

/**
 * Created by pzzheng on 12/28/16.
 */
public class ObjectHandler extends AbstractTypeHandler<Document, Object> {
    @Override
    protected Converter<Document, Object> encode(Document sourceObj) {
        return null;
    }

    @Override
    protected Converter<Object, Document> decode(Object targetObj) {
        return null;
    }
}
