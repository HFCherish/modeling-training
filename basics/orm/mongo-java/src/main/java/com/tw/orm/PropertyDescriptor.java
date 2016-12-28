package com.tw.orm;

/**
 * Created by pzzheng on 12/28/16.
 */
public class PropertyDescriptor {
    private String propertyName;
    private String fieldName;
    private Class<?> propertyType;
    boolean isId;

    public PropertyDescriptor(String fieldName, String propertyName, Class<String> propertyType) {
        this.fieldName = fieldName;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }
}
