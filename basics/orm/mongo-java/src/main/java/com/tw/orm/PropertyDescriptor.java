package com.tw.orm;

/**
 * Created by pzzheng on 12/28/16.
 */
public class PropertyDescriptor {
    private String propertyName;
    private String fieldName;
    private Class<?> propertyType;
    private boolean isId;

    public PropertyDescriptor(String fieldName, String propertyName, Class<?> propertyType, boolean isId) {
        this.fieldName = fieldName;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.isId = isId;
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
}
