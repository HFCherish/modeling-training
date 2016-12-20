package com.tw.ioc;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Binding<T> binding = binder.getBinding(toInjectClass);
        if (binding == null) {
            return getInstanceFromConstructor(toInjectClass);
        }
        return binding.getProvider().get();
    }

    private <T> T getInstanceFromConstructor(Class<T> toInjectClass) {
        try {
            Constructor<T> constructor = toInjectClass.getConstructor(new Class[0]);
            if (constructor != null) {
                constructor.setAccessible(true);
                return constructor.newInstance(new Object[0]);
            }
        } catch (NoSuchMethodException e) {
            return getInstanceFromInjectConstructor(toInjectClass);
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
        List<Constructor<?>> constructors = Arrays.stream(toInjectClass.getConstructors()).filter(constructor -> constructor.isAnnotationPresent(Inject.class)).collect(Collectors.toList());

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
