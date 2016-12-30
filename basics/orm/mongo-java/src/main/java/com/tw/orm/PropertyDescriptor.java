package com.tw.orm;

/**
 * Created by pzzheng on 12/28/16.
 */
public class PropertyDescriptor {
    private String propertyName;    //pojo property
    private String fieldName;   //mongo field
    private Class<?> propertyType;
    private Class<?> fieldType;
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

    // TODO: 12/30/16 modify to use specified storage type
    public Class<?> getFieldType() {
        return Object.class;
    }
}
