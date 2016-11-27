package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.products.Product;
import com.thoughtworks.shopping.domain.products.ProductRepo;
import com.thoughtworks.shopping.domain.users.Order;
import com.thoughtworks.shopping.domain.users.OrderItem;
import com.thoughtworks.shopping.domain.users.OrderRepo;
import com.thoughtworks.shopping.domain.users.User;
import com.thoughtworks.shopping.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.thoughtworks.shopping.web.validators.Validators.*;

/**
 * Created by pzzheng on 11/22/16.
 */
public class OrdersApi {
    private User user;

    public OrdersApi(User user) {
        this.user = user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response buildOrder(Map<String, Object> info,
                               @Context OrderRepo orderRepo,
                               @Context Routes routes,
                               @Context ProductRepo productRepo) {
        validate(info, all(collectionFieldNotEmpty("order_items", "must contains at least one order item")));
        for(Map item: (List<Map>)info.get("order_items")) {
            validate(item, all(fieldNotEmpty("product", "product id is required"),
                    fieldNotEmpty("quantity", "product quantity is required")));
        }

        List<OrderItem> items = new ArrayList<>();
        for(Map item: (List<Map>)info.get("order_items")) {
            Optional<Product> product = productRepo.getProduct(item.get("product").toString());
            if(!product.isPresent())
                throw new BadRequestException("product " + item.get("product") + " does not exists");
            items.add(new OrderItem(product.get(), (int)item.get("quantity")));
        }

        Order save = orderRepo.save(new Order(user, items));
        return Response.status(201).location(routes.orderUrl(user.getId().id(), save.getId().id())).build();
    }

    @GET
    public List<Order> getOrders() {
        return null;
    }

    @Path("{oid}")
    public OrderApi toOrderApi(@PathParam("oid") String oid,
                               @Context OrderRepo orderRepo) {
        return orderRepo.findById(oid).map(OrderApi::new).orElseThrow(() -> new NotFoundException("order does not exists"));
    }
}
