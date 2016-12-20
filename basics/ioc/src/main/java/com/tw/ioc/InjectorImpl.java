package com.tw.ioc;

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
        return binding.getProvider().get();
    }
}
