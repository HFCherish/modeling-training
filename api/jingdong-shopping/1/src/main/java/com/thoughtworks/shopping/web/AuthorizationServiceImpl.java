package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.users.AuthorizationService;
import com.thoughtworks.shopping.domain.users.CurrentUser;
import com.thoughtworks.shopping.domain.users.User;
import com.thoughtworks.shopping.domain.users.UserRole;

import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;

/**
 * Created by pzzheng on 11/23/16.
 */
public class AuthorizationServiceImpl implements AuthorizationService{
    @Inject
    CurrentUser currentUserService;

    @Override
    public boolean checkCanWriteProductOf(User user) {
        User currentUser = this.currentUserService.get();
        if(!currentUser.getRole().equals(UserRole.SELLER.name())) {
            throw new ForbiddenException("you are not a seller, cannot write product");
        }
        if(!currentUser.equals(user)) {
            throw new ForbiddenException("you can only write products of yourself");
        }
        return true;
    }

    @Override
    public boolean checkCanAccessOrder(User user) {
        User currentUser = this.currentUserService.get();
        if(!currentUser.equals(user)) {
            throw new ForbiddenException("can only access order of yourself");
        }
        return true;
    }
}
