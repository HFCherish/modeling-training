package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.user.User;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

public class UserApi {
    private User user;

    public UserApi(User user) {
        this.user = user;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@Context AuthorizationService authorizationService) {
        if( !authorizationService.currentUserIs(user)) {
            throw new NotFoundException("user not exists");
        }
        return user;
    }

    @Path("calls")
    public CallsApi toCallsApi() {
        return new CallsApi(user);
    }

    @Path("messages")
    public MessagesApi toMessagesApi() {
        return new MessagesApi(user);
    }

    @Path("data_accesses")
    public DataAccessesApi todataAccessesApi() {
        return new DataAccessesApi(user);
    }
}
