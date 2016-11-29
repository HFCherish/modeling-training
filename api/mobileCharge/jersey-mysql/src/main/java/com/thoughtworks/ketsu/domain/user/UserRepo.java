package com.thoughtworks.ketsu.domain.user;

import com.thoughtworks.ketsu.domain.Existent;

import java.util.Optional;

/**
 * Created by pzzheng on 11/29/16.
 */
public interface UserRepo extends Existent<String, User>{
    @Override
    Optional<User> findBy(String id);
    User save(User user);
}
