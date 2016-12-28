package com.tw.orm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pzzheng on 12/28/16.
 */
public class ObjectDescriptor {
    private Class<?> type;
    private String id;
    private Map<String, PropertyDescriptor> propertyDescriptors = new HashMap<>();

    public ObjectDescriptor(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    void addPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
        propertyDescriptors.put(propertyDescriptor.getPropertyName(), propertyDescriptor);
    }

    public PropertyDescriptor getPropertyDescriptor(String propertyName) {
        return propertyDescriptors.get(propertyName);
    }
}
