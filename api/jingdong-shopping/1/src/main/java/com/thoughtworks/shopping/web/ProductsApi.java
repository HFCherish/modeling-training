package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.products.Product;
import com.thoughtworks.shopping.domain.products.ProductRepo;
import com.thoughtworks.shopping.domain.users.AuthorizationService;
import com.thoughtworks.shopping.domain.users.CurrentUser;
import com.thoughtworks.shopping.domain.users.User;
import com.thoughtworks.shopping.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static com.thoughtworks.shopping.web.validators.Validators.all;
import static com.thoughtworks.shopping.web.validators.Validators.fieldNotEmpty;
import static com.thoughtworks.shopping.web.validators.Validators.validate;

/**
 * Created by pzzheng on 11/22/16.
 */

public class ProductsApi {
    private User user;

    public ProductsApi(User user) {
        this.user = user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(Map<String, Object> info,
                                  @Context AuthorizationService authorizationService,
                                  @Context CurrentUser currentUser,
                                  @Context ProductRepo productRepo,
                                  @Context Routes routes) {
        validate(info, all(fieldNotEmpty("name", "name is required"),
                fieldNotEmpty("description", "description is required")));

        authorizationService.checkCanWriteProductOf(user);

        Product product = productRepo.createProduct(new Product(info.get("name").toString(), info.get("description").toString(), user));
        return Response.status(201).location(routes.productUrl(user.getId().id(), product.getId().id())).build();
    }

    @GET
    public List<Product> getProducts() {
        return null;
    }

    @Path("{pid}")
    public ProductApi toProductApi(@PathParam("pid") String pid,
                                   @Context ProductRepo productRepo) {
        return productRepo.getProduct(pid).map(ProductApi::new).orElseThrow(() -> new NotFoundException("product does not exists"));
    }

}
