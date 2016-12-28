package com.tw.orm;

/**
 * Created by pzzheng on 12/28/16.
 */
public class ObjectDescriptor {
    Class<?> type;
    String id;

    public ObjectDescriptor(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    void addPropertyDescriptor(PropertyDescriptor propertyDescriptor) {

    }
}
