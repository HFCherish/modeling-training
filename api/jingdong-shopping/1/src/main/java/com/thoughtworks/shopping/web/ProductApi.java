package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.products.Product;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by pzzheng on 11/22/16.
 */
public class ProductApi {
    private Product product;

    public ProductApi(Product product) {
        this.product = product;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProduct() {
        return product;
    }

    @Path("unlist")
    @PUT
    public Response unList() {
        return null;
    }
}
