package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.user.UserRepo;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

@Path("users")
public class UsersApi {

    @Path("{userId}")
    public UserApi getUser(@PathParam("userId") String userId,
                           @Context UserRepo userRepo) {
        return userRepo.findById(userId).map(UserApi::new).orElseThrow(() -> new NotFoundException("user not exists."));
    }
}
