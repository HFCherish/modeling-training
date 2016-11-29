package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.user.User;

/**
 * Created by pzzheng on 11/29/16.
 */
public class MessagesApi {
    private User user;

    public MessagesApi(User user) {
        this.user = user;
    }
}
