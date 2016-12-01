package com.thoughtworks.mobileCharge.api;

import com.thoughtworks.mobileCharge.domain.user.User;

/**
 * Created by pzzheng on 11/29/16.
 */
public interface AuthorizationService {
    boolean currentUserIs(User user);
}
