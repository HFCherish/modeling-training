package com.tw.orm;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Collectors;

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

            //set properties, going up the class hierarchy to set all declared properties.
            Class<?> currentClass = targetClass;
            while (currentClass != null) {
                ObjectDescriptor objectDescriptor = objectMapper.getDescriptor(currentClass);
                if (objectDescriptor == null)
                    break;
                objectDescriptor.getProperties().stream().forEach(pd -> {
                    Object propertyValue = document.get(pd.getFieldName());
                    if (propertyValue instanceof Collection) {
                        Type genericType = pd.getProperty().getGenericType();
                        Class<?> propertyTypeParameter = genericType instanceof ParameterizedType ? (Class) (((ParameterizedType) genericType).getActualTypeArguments()[0]) : Object.class;
                        propertyValue = ((Collection) propertyValue).stream().map(o -> conversionContext.convert(o, propertyTypeParameter)).collect(Collectors.toList());
                    } else {
                        propertyValue = conversionContext.convert(propertyValue, pd.getPropertyType());
                    }
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
            Class<?> currentClass = sourceClass;
            while (currentClass != null) {
                ObjectDescriptor objectDescriptor = objectMapper.getDescriptor(currentClass);
                if (objectDescriptor == null) break;
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

                currentClass = currentClass.getSuperclass();
            }
            return document;
        };
    }

}
