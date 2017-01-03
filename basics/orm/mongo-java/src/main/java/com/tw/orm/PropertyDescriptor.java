package com.tw.orm;

import java.lang.reflect.Field;

/**
 * Created by pzzheng on 12/28/16.
 */
public class PropertyDescriptor {
    private String propertyName;    //pojo property
    private String fieldName;   //mongo field
    private Class<?> propertyType;
    private Class<?> fieldType;
    private boolean isId;
    private Field property;

    public PropertyDescriptor(String fieldName, String propertyName, Class<?> propertyType, boolean isId) {
        this.fieldName = fieldName;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.isId = isId;
    }

    public PropertyDescriptor(String fieldName, boolean isId, Field property) {
        this.fieldName = fieldName;
        this.propertyName = property.getName();
        this.propertyType = property.getType();
        this.isId = isId;
        this.property = property;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean isId() {
        return isId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public Field getProperty() {
        return property;
    }
}
