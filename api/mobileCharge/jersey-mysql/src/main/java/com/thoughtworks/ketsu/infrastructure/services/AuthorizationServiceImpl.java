package com.thoughtworks.ketsu.infrastructure.services;

import com.thoughtworks.ketsu.domain.CurrentUserService;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.web.AuthorizationService;
import com.thoughtworks.ketsu.web.exception.NoAuthenticationException;

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
