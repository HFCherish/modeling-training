package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.user.User;

/**
 * Created by pzzheng on 11/29/16.
 */
public interface AuthorizationService {
    boolean currentUserIs(User user);
}
