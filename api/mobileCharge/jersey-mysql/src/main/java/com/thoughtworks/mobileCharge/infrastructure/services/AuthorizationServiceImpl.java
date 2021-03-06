package com.thoughtworks.mobileCharge.infrastructure.services;

import com.thoughtworks.mobileCharge.domain.CurrentUserService;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.api.AuthorizationService;
import com.thoughtworks.mobileCharge.api.exception.NoAuthenticationException;

import javax.inject.Inject;

/**
 * Created by pzzheng on 11/29/16.
 */
public class AuthorizationServiceImpl implements AuthorizationService {
    @Inject
    CurrentUserService currentUserService;

    @Override
    public boolean currentUserIs(User user) {
        User currentUser = currentUserService.currentUser().orElseThrow(() -> new NoAuthenticationException());
        return currentUser.equals(user);
    }
}
