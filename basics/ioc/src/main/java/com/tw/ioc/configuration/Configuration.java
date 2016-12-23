package com.tw.ioc.configuration;

import com.tw.ioc.binder.Binder;

/**
 * Created by pzzheng on 12/19/16.
 */
public interface Configuration {
    void configure(Binder binder);
}
