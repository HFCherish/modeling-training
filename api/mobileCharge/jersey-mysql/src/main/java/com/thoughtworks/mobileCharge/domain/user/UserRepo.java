package com.thoughtworks.mobileCharge.domain.user;

import java.util.List;
import java.util.Optional;

/**
 * Created by pzzheng on 11/29/16.
 */
public interface UserRepo {
    Optional<User> findById(String id);
    List<User> findAll();
//    User save(User user);
}
