package com.tw.ioc;

import javax.inject.Named;
import java.lang.annotation.Annotation;

/**
 * Created by pzzheng on 12/20/16.
 */
public class Names {
    public static Named named(String value) {
        return new NamedImpl(value);
    }

    static class NamedImpl implements Named{
        private String value;

        public NamedImpl(String value) {
            this.value = value;
        }

        @Override
        public String value() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NamedImpl named = (NamedImpl) o;

            return value.equals(named.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return Named.class;
        }
    }
}
