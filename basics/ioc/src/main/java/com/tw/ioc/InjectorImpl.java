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
        if(binding == null) {
            try {
                Constructor<T> constructor = toInjectClass.getConstructor(new Class[0]);
                if(constructor != null ) {
                    constructor.setAccessible(true);
                    return constructor.newInstance(new Object[0]);
                }
            } catch (NoSuchMethodException e) {
                Stream<Constructor<?>> constructorStream = Arrays.stream(toInjectClass.getConstructors()).filter(constructor -> constructor.isAnnotationPresent(Inject.class));
                Optional<Constructor<?>> injectConstructor = constructorStream.findFirst();
                if(injectConstructor.isPresent()) {
                    Constructor<T> constructor = (Constructor<T>) injectConstructor.get();
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
                }
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return binding.getProvider().get();
    }
}
