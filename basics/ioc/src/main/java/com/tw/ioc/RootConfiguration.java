package com.tw.ioc;

import static com.tw.ioc.Scopes.SINGLETON;

/**
 * Created by pzzheng on 12/20/16.
 */
public class RootConfiguration implements Configuration {
    @Override
    public void configure(Binder binder) {
        binder.bindScope(javax.inject.Singleton.class, SINGLETON);
    }
}
