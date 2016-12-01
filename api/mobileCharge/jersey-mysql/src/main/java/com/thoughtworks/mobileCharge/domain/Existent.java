package com.thoughtworks.mobileCharge.domain;

import java.util.Optional;

/**
 * Created by pzzheng on 11/29/16.
 */
public interface Existent<K, V> {
    Optional<V> findBy(K k);
}
