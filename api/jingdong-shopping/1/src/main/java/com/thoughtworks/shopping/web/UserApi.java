package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.users.AuthorizationService;
import com.thoughtworks.shopping.domain.users.CurrentUser;
import com.thoughtworks.shopping.domain.users.User;
import com.thoughtworks.shopping.domain.users.UserRepo;
import com.thoughtworks.shopping.web.validators.Validators;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

import static com.thoughtworks.shopping.web.validators.Validators.all;
import static com.thoughtworks.shopping.web.validators.Validators.fieldNotEmpty;
import static com.thoughtworks.shopping.web.validators.Validators.validate;

/**
 * Created by pzzheng on 11/22/16.
 */
public class UserApi {
    private User user;

    public UserApi(User user) {
        this.user = user;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@Context CurrentUser currentUser) {
        if (!currentUser.get().getId().equals(user.getId())) {
            throw new ForbiddenException("has no right to see others profile");
        }
        return user;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePassword(Map<String, Object> info,
                                   @Context CurrentUser currentUser,
                                   @Context UserRepo userRepo) {
        if (!currentUser.get().getId().equals(user.getId())) {
            throw new ForbiddenException("has no right to modify others profile");
        }

        Validators.Validator userValidator = all(fieldNotEmpty("password", "password is required"));
        validate(info, userValidator);

        userRepo.update(info);
        return Response.status(204).build();
    }

    @Path("session")
    public SessionApi toSessionApi() {
        return new SessionApi(user);
    }

    @Path("orders")
    public OrdersApi toOrdersApi(@Context AuthorizationService authorizationService) {
        authorizationService.checkCanAccessOrder(user);
        return new OrdersApi(user);
    }

    @Path("products")
    public ProductsApi toProductsApi() {
        return new ProductsApi(user);
    }
}
