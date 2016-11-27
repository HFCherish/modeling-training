package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.users.User;
import com.thoughtworks.shopping.domain.users.UserRepo;
import com.thoughtworks.shopping.domain.users.UserRole;
import com.thoughtworks.shopping.web.jersey.Routes;
import com.thoughtworks.shopping.web.validators.Validators;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

import static com.thoughtworks.shopping.web.validators.Validators.all;
import static com.thoughtworks.shopping.web.validators.Validators.fieldNotEmpty;
import static com.thoughtworks.shopping.web.validators.Validators.unique;

/**
 * Created by pzzheng on 11/22/16.
 */
@Path("/users")
public class UsersApi {


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(Map<String, Object> info,
                             @Context UserRepo userRepo,
                             @Context Routes routes) {
        Validators.Validator userValidator = all(fieldNotEmpty("password", "password is required"),
                fieldNotEmpty("email", "email is required"),
                unique("email", "email has been taken", userRepo));
        Validators.validate(info, userValidator);

        User user = userRepo.save(new User(info.get("email").toString(), info.get("password").toString(), UserRole.BUYER));
        return Response.status(201).location(routes.userUrl(user.getId().id())).build();
    }

    @Path("{uid}")
    public UserApi toUserApi(@PathParam("uid") String uid,
                             @Context UserRepo userRepo) {
        return userRepo.findById(uid).map(UserApi::new).orElseThrow(() -> new NotFoundException("user does not exists"));
    }

}
