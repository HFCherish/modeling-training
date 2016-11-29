package com.thoughtworks.ketsu.domain;

import com.thoughtworks.ketsu.domain.user.User;

import java.util.Optional;

/**
 * Created by pzzheng on 11/29/16.
 */
public interface CurrentUserService {
    Optional<User> currentUser();
}
