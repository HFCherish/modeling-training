package com.tw.ioc;

/**
 * Created by pzzheng on 12/19/16.
 */
public class DI {
    public static Injector createInjector(Configuration configuration) {
        RecordBinder recordBinder = new RecordBinder();
        new RootConfiguration().configure(recordBinder);
        configuration.configure(recordBinder);
        return new InjectorImpl(recordBinder);
    }
}
