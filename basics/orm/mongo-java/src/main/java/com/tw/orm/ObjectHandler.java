package com.tw.orm;

import org.bson.Document;

import java.lang.reflect.Field;

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
            res = ReflectionUtil.instanceFromEmptyConstructor(targetClass);

            //set properties
            ObjectDescriptor objectDescriptor = objectMapper.getDescriptor(targetClass);
            objectDescriptor.getProperties().stream().forEach(pd -> {
                Object propertyValue = document.get(pd.getFieldName(), pd.getPropertyType());
                ReflectionUtil.setField(res, propertyValue, pd.getPropertyName());
            });
            return res;
        };
    }

    @Override
    protected Converter<Object, Document> unmap(Class<?> sourceClass, Class<?> targetClass) {
        return object -> {
            Document document = new Document();
            ObjectDescriptor objectDescriptor = objectMapper.getDescriptor(sourceClass);
            objectDescriptor.getProperties().stream().forEach(pd -> {
                Object propertyValue = null;
                try {
                    Field property = sourceClass.getDeclaredField(pd.getPropertyName());
                    if(!property.isAccessible()) {
                        property.setAccessible(true);
                    }
                    propertyValue = property.get(object);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e.getMessage());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if(propertyValue != null) {
                    document.append(pd.getFieldName(), propertyValue);
                }
            });

            return document;
        };
    }

}
