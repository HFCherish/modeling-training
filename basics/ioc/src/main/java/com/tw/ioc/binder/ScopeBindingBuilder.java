package com.tw.ioc.binder;

import com.tw.ioc.Scope;

/**
 * Created by pzzheng on 12/20/16.
 */
public interface ScopeBindingBuilder<T> {
    void in(Scope scope);
}
