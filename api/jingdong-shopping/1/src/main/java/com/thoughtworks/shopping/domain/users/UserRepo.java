package com.thoughtworks.shopping.domain.users;

import com.thoughtworks.shopping.Uniqueness;

import java.util.Map;
import java.util.Optional;

/**
 * Created by pzzheng on 11/22/16.
 */
public interface UserRepo extends Uniqueness<String, User>{
    Optional<User> findByEmail(String email);

    User save(User user);

    Optional<User> findBy(String email);

    Optional<User> findById(String uid);

    User update(Map<String, Object> info);
}
