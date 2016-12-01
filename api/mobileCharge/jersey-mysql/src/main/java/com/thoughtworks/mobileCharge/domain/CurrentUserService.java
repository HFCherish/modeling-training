package com.thoughtworks.mobileCharge.domain;

import com.thoughtworks.mobileCharge.domain.user.User;

import java.util.Optional;

/**
 * Created by pzzheng on 11/29/16.
 */
public interface CurrentUserService {
    Optional<User> currentUser();
}
