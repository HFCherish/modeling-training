package com.tw.orm;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Optional<Converter> getConverter(ConversionType sourceType, ConversionType targetType) {
        if ((sourceType.getClazz().equals(Document.class) && objectMapper.hasDescriptor(targetType.getClazz()))) {
            return Optional.of(map(sourceType, targetType));
        }
        if (targetType.getClazz().equals(Document.class) && objectMapper.hasDescriptor(sourceType.getClazz())) {
            return Optional.of(unmap(sourceType, targetType));
        }
        return Optional.empty();
    }

    @Override
    protected Converter<Document, Object> map(ConversionType sourceType, ConversionType targetType) {
        return (document, conversionContext) -> {
            //create the object
            Object res;
            res = ReflectionUtil.instanceFromEmptyConstructor(targetType.getClazz());

            //set properties, going up the class hierarchy to set all declared properties.
            Class<?> currentClass = targetType.getClazz();
            while (currentClass != null) {
                ObjectDescriptor objectDescriptor = objectMapper.getDescriptor(currentClass);
                if (objectDescriptor == null)
                    break;
                objectDescriptor.getProperties().stream().forEach(pd -> {
                    Object propertyValue = document.get(pd.getFieldName());
                    //collection/array field
                    if (propertyValue instanceof Collection || (propertyValue != null && propertyValue.getClass().isArray())) {
                        Stream<Object> stream;
                        Type genericType = pd.getProperty().getGenericType();
                        Class<?> propertyTypeParameter = genericType instanceof ParameterizedType ? (Class) (((ParameterizedType) genericType).getActualTypeArguments()[0]) : Object.class;
                        if (propertyValue instanceof Collection) {
                            stream = ((Collection) propertyValue).stream();
                        } else {
                            stream = Arrays.stream((Object[]) propertyValue);
                        }
                        propertyValue = stream.map(o -> conversionContext.convert(o, ConversionType.of(propertyTypeParameter))).collect(Collectors.toList());
                    }
                    //non-collection field
                    else {
                        propertyValue = conversionContext.convert(propertyValue, ConversionType.of(pd.getPropertyType()));
                    }
                    ReflectionUtil.setProperty(res, propertyValue, pd.getPropertyName());
                });
                currentClass = currentClass.getSuperclass();
            }

            return res;
        };

    }

    @Override
    protected Converter<Object, Document> unmap(ConversionType sourceType, ConversionType targetType) {
        return (object, conversionContext) -> {
            Document document = new Document();
            Class<?> currentClass = sourceType.getClazz();
            while (currentClass != null) {
                ObjectDescriptor objectDescriptor = objectMapper.getDescriptor(currentClass);
                if (objectDescriptor == null) break;
                objectDescriptor.getProperties().stream().forEach(pd -> {
                    Object propertyValue = ReflectionUtil.getPropertyValue(object, pd.getPropertyName());
                    if (propertyValue != null) {
                        Class<?> fieldType = pd.getFieldType() != null ? pd.getFieldType() : propertyValue.getClass();
                        propertyValue = conversionContext.convert(propertyValue, ConversionType.of(fieldType));
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
