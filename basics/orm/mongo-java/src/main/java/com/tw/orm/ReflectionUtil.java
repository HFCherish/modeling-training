package com.tw.orm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by pzzheng on 12/29/16.
 */
public class ReflectionUtil {

    public static Object instanceFromEmptyConstructor(Class<?> targetClass) {
        Object res;
        try {
            Constructor<?> emptyConstructor = targetClass.getDeclaredConstructor(new Class[0]);
            if (!emptyConstructor.isAccessible()) {
                emptyConstructor.setAccessible(true);
            }
            res = emptyConstructor.newInstance(new Object[0]);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.fillInStackTrace());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.fillInStackTrace());
        } catch (InstantiationException e) {
            throw new RuntimeException(e.fillInStackTrace());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
        return res;
    }

    public static void setProperty(Object targetObject, Object propertyValue, String propertyName) {
        try {
            Field property = getDeclaredField(propertyName, targetObject.getClass());
            if (!property.isAccessible()) {
                property.setAccessible(true);
            }
            property.set(targetObject, propertyValue);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e.fillInStackTrace());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    public static Object getPropertyValue(Object object, String propertyName) {
        Object propertyValue;
        try {
            Field property = getDeclaredField(propertyName, object.getClass());
            if (!property.isAccessible()) {
                property.setAccessible(true);
            }
            propertyValue = property.get(object);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e.fillInStackTrace());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
        return propertyValue;
    }

    /**
     * get field whose name is propertyName from aClass, going up the class hierarchy to get all declared fields.
     *
     * @param propertyName
     * @param aClass
     * @return
     * @throws NoSuchFieldException
     */
    private static Field getDeclaredField(String propertyName, Class<?> aClass) throws NoSuchFieldException {
        Class<?> currentClass = aClass;
        while (currentClass != null) {
            try {
                Field field = currentClass.getDeclaredField(propertyName);
                return field;
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }
        throw new NoSuchFieldException(propertyName);
    }

    public static Collection<Object> instanceOfCollection(ConversionType type) {
        if (Collection.class.isAssignableFrom(type.getClazz())) return null;
        // TODO: 1/4/17 modify to return according to class
        return new ArrayList();
    }
}
