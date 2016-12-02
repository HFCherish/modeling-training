package com.thoughtworks.mobileCharge.api;

import com.thoughtworks.mobileCharge.domain.user.Balance;
import com.thoughtworks.mobileCharge.domain.user.User;

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
        if (!authorizationService.currentUserIs(user)) {
            throw new NotFoundException("user not exists");
        }
        return user;
    }

    @GET
    @Path("balance")
    @Produces(MediaType.APPLICATION_JSON)
    public Balance getBalance(@Context AuthorizationService authorizationService) {
        if (!authorizationService.currentUserIs(user)) {
            throw new NotFoundException("user not exists");
        }
        return null;
    }

    @Path("calls")
    public CallRecordsApi toCallsApi(@Context AuthorizationService authorizationService) {
        if (!authorizationService.currentUserIs(user)) {
            throw new NotFoundException("user not exists");
        }
        return new CallRecordsApi(user);
    }

    @Path("messages")
    public MessagesApi toMessagesApi(@Context AuthorizationService authorizationService) {
        if (!authorizationService.currentUserIs(user)) {
            throw new NotFoundException("user not exists");
        }
        return new MessagesApi(user);
    }

    @Path("data_accesses")
    public DataAccessesApi todataAccessesApi(@Context AuthorizationService authorizationService) {
        if (!authorizationService.currentUserIs(user)) {
            throw new NotFoundException("user not exists");
        }
        return new DataAccessesApi(user);
    }
}
