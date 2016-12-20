package com.tw.ioc;

/**
 * Created by pzzheng on 12/19/16.
 */
public class DI {
    public static Injector createInjector(Configuration configuration) {
        RecordBinder recordBinder = new RecordBinder();
        configuration.configure(recordBinder);
        return new InjectorImpl(recordBinder);
    }
}
