package com.tw.orm;

import org.bson.Document;
import org.bson.types.ObjectId;

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
    protected Converter<Document, Object> map(Class<?> sourceClass, Class<?> targetClass, ConversionContext conversionContext) {
        return document -> {
            //create the object
            Object res;
            res = ReflectionUtil.instanceFromEmptyConstructor(targetClass);

            //set properties
            Class<?> currentClass = targetClass;
            while( currentClass != null) {
                ObjectDescriptor objectDescriptor = objectMapper.getDescriptor(currentClass);
                if( objectDescriptor == null)
                    break;
                objectDescriptor.getProperties().stream().forEach(pd -> {
                    Object propertyValue = document.get(pd.getFieldName());
                    propertyValue = conversionContext.convert(propertyValue, pd.getPropertyType());
                    ReflectionUtil.setProperty(res, propertyValue, pd.getPropertyName());
                });
                currentClass = currentClass.getSuperclass();
            }
            return res;
        };
    }

    @Override
    protected Converter<Object, Document> unmap(Class<?> sourceClass, Class<?> targetClass, ConversionContext conversionContext) {
        return object -> {
            Document document = new Document();
            ObjectDescriptor objectDescriptor = objectMapper.getDescriptor(sourceClass);
            objectDescriptor.getProperties().stream().forEach(pd -> {
                Object propertyValue = ReflectionUtil.getPropertyValue(object, pd.getPropertyName());
                if (propertyValue != null) {
                    Class<?> fieldType = pd.getFieldType() != null ? pd.getFieldType() : propertyValue.getClass();
                    propertyValue = conversionContext.convert(propertyValue, fieldType);
                    document.append(pd.getFieldName(), propertyValue);
                }
            });

            if (document.get("_id") == null) {
                document.append("_id", new ObjectId());
            }
            return document;
        };
    }

}
