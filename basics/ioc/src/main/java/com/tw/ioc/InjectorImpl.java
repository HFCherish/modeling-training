package com.tw.ioc;

import com.tw.ioc.util.AnnotationHelper;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tw.ioc.util.AnnotationHelper.findAnnotationByMetaAnnotationType;

/**
 * Created by pzzheng on 12/20/16.
 */
public class InjectorImpl implements Injector {
    private Binder binder;

    public InjectorImpl(Binder binder) {
        this.binder = binder;
    }

    @Override
    public <T> T getInstance(Class<T> toInjectClass) {
        return getInstance(Key.of(toInjectClass));
    }

    @Override
    public <T> T getInstance(Key<T> key) {
        Binding<T> binding = binder.getBinding(key);
        T res;
        res = binding == null ? getInstanceFromConstructor(key.getToInjectClass()) : binding.getProvider().get();
        injectMembers(res);
        return res;
    }

    @Override
    public void injectMembers(Object instance) {
        if (instance == null) return;
        injectFields(instance);
        injectMethods(instance);

    }

    private void injectMethods(Object instance) {
        List<Method> injectMethods = Arrays.stream(instance.getClass().getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Inject.class)).collect(Collectors.toList());
        injectMethods.stream().forEach(method -> {
            method.setAccessible(true);
            List<?> parameters = Arrays.stream(method.getParameters()).map(parameter -> {
                Annotation qualifier = null;
                Optional<Annotation> qualifierOptional = AnnotationHelper.findAnnotationByMetaAnnotationType(parameter.getAnnotations(), Qualifier.class);
                if(qualifierOptional.isPresent()) {
                    qualifier = qualifierOptional.get();
                }
                return getInstance(Key.of(parameter.getType(), qualifier));
            }).collect(Collectors.toList());
            try {
                method.invoke(instance, parameters.toArray());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private void injectFields(Object instance) {
        List<Field> injectFields = Arrays.stream(instance.getClass().getDeclaredFields()).filter(field -> field.isAnnotationPresent(Inject.class)).collect(Collectors.toList());
        injectFields.stream().forEach(field -> {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            try {
                Annotation qualifier = null;
                Optional<Annotation> qualifierOptional = AnnotationHelper.findAnnotationByMetaAnnotationType(field.getAnnotations(), Qualifier.class);
                if(qualifierOptional.isPresent()) {
                    qualifier = qualifierOptional.get();
                }
                field.set(instance, getInstance(Key.of(fieldType, qualifier)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private <T> T getInstanceFromConstructor(Class<T> toInjectClass) {
        try {
            Constructor<T> constructor = toInjectClass.getDeclaredConstructor(new Class[0]);
            if (constructor != null) {
                constructor.setAccessible(true);
                return constructor.newInstance(new Object[0]);
            }
        } catch (NoSuchMethodException e) {
            T res = getInstanceFromInjectConstructor(toInjectClass);
            if (res != null) return res;
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T getInstanceFromInjectConstructor(Class<T> toInjectClass) {
        List<Constructor<?>> constructors = Arrays.stream(toInjectClass.getDeclaredConstructors()).filter(constructor -> constructor.isAnnotationPresent(Inject.class)).collect(Collectors.toList());

        if (constructors.size() > 1) {
            try {
                throw new InstantiationException("can only annotate one constructor with Inject");
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            }
            return null;
        }
        if (constructors.size() < 0) {
            try {
                throw new NoSuchMethodException("no empty constructor or construction with injection");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }
        Constructor<T> constructor = (Constructor<T>) constructors.get(0);
        List<?> parameters = Arrays.stream(constructor.getParameterTypes()).map(type -> getInstance(type)).collect(Collectors.toList());
        constructor.setAccessible(true);
        try {
            return constructor.newInstance(parameters.toArray());
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
