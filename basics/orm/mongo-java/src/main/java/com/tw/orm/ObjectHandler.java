package com.tw.orm;

import org.bson.Document;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by pzzheng on 12/28/16.
 */
public class ObjectHandler extends AbstractTypeHandler<Document, Object> {
    private ObjectMapper objectMapper;

    public ObjectHandler(ObjectMapper objectMapper) {
        super(Document.class, Object.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean canConvert(Class<?> sourceClass, Class<?> targetClass) {
        return (sourceClass.equals(Document.class) && objectMapper.hasDescriptor(targetClass)) ||
                (targetClass.equals(Document.class) && objectMapper.hasDescriptor(sourceClass));
    }

    @Override
    protected Converter<Document, Object> map(Class<?> sourceClass, Class<?> targetClass) {
        return document -> {
            //create the object
            Object res;
            try {
                Constructor<?> emptyConstructor = targetClass.getDeclaredConstructor(new Class[0]);
                if(!emptyConstructor.isAccessible()) {
                    emptyConstructor.setAccessible(true);
                }
                res = emptyConstructor.newInstance(new Object[0]);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e.getMessage());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage());
            } catch (InstantiationException e) {
                throw new RuntimeException(e.getMessage());
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getMessage());
            }

            //set properties
            ObjectDescriptor objectDescriptor = objectMapper.getDescriptor(targetClass);
            objectDescriptor.getProperties().stream().forEach(pd -> {
                Object propertyValue = document.get(pd.getFieldName(), pd.getPropertyType());
                try {
                    Field property = res.getClass().getDeclaredField(pd.getPropertyName());
                    if(!property.isAccessible()) {
                        property.setAccessible(true);
                    }
                    property.set(res, propertyValue);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e.getMessage());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage());
                }
            });
            return res;
        };
    }

    @Override
    protected Converter<Object, Document> unmap(Class<?> sourceClass, Class<?> targetClass) {
        return object -> {

            return null;
        };
    }

}
