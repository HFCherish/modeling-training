package com.tw.ioc;

/**
 * Created by pzzheng on 12/20/16.
 */
public class Scopes {
    public static Scope SINGLETON = new Scope() {
        @Override
        public <T> Provider<T> scope(Provider<T> unScopedProvider) {
            T instance = unScopedProvider.get();
            return () -> instance;
        }
    };
}
