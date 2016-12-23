package com.tw.ioc;

import com.tw.ioc.binder.RecordBinder;
import com.tw.ioc.configuration.Configuration;
import com.tw.ioc.configuration.RootConfiguration;

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
