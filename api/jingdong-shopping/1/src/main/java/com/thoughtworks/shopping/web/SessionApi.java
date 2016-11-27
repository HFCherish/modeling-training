package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.users.User;

/**
 * Created by pzzheng on 11/22/16.
 */
public class SessionApi {
    private User user;

    public SessionApi(User user) {
        this.user = user;
    }
}
