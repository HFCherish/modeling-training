package com.tw.orm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by pzzheng on 12/29/16.
 */
public class ReflectionUtil {

    public static Object instanceFromEmptyConstructor(Class<?> targetClass) {
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
        return res;
    }

    public static void setProperty(Object targetObject, Object propertyValue, String propertyName) {
        try {
            Field property = targetObject.getClass().getDeclaredField(propertyName);
            if(!property.isAccessible()) {
                property.setAccessible(true);
            }
            property.set(targetObject, propertyValue);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Object getPropertyValue(Object object, String propertyName) {
        Object propertyValue;
        try {
            Field property = object.getClass().getDeclaredField(propertyName);
            if(!property.isAccessible()) {
                property.setAccessible(true);
            }
            propertyValue = property.get(object);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
        return propertyValue;
    }
}
