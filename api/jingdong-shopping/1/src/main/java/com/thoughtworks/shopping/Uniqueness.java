package com.thoughtworks.shopping;

import java.util.Optional;

/**
 * Created by pzzheng on 11/23/16.
 */
public interface Uniqueness<Key, Entity> {
    Optional<Entity> findBy(Key key);

}
