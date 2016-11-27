package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.users.Order;
import com.thoughtworks.shopping.domain.users.OrderItem;
import com.thoughtworks.shopping.web.jersey.Routes;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

import static com.thoughtworks.shopping.web.validators.Validators.all;
import static com.thoughtworks.shopping.web.validators.Validators.fieldNotEmpty;
import static com.thoughtworks.shopping.web.validators.Validators.validate;

/**
 * Created by pzzheng on 11/25/16.
 */
public class RefundRequestApi {
    private Order order;
    private OrderItem orderItem;

    public RefundRequestApi(Order order, OrderItem orderItem) {
        this.order = order;

        this.orderItem = orderItem;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response requestRefund(Map<String, Object> info,
                                  @Context Routes routes) {
        validate(info, all(fieldNotEmpty("reason", "input the reason for refund"), fieldNotEmpty("operation", "select to refund or reshopping"), fieldNotEmpty("quantity", "select quantity")));
        if(!order.isPayed()) {
            throw new WebApplicationException("refund after pay", Response.Status.CONFLICT);
        }
        orderItem.createRefundRequest(info);
        return Response.status(201).location(routes.orderUrl(order.getUser().getId().id(), order.getId().id())).build();
    }
}
