package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.users.Order;
import com.thoughtworks.shopping.web.exception.ConflictException;
import com.thoughtworks.shopping.web.jersey.Routes;

import javax.print.attribute.standard.Media;
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
public class DeliveryConfirmationApi {
    private Order order;

    public DeliveryConfirmationApi(Order order) {
        this.order = order;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response confirmOrder(Map<String, Object> info,
                                 @Context Routes routes) {
        validate(info, all(fieldNotEmpty("confirmation", "must response to the confirmation")));
        if(!order.isPayed()) {
            throw new WebApplicationException("confirm after pay", Response.Status.CONFLICT);
        }
        order.confirmDelivery(info);
        return Response.status(201).location(routes.orderUrl(order.getUser().getId().id(), order.getId().id())).build();
    }
}
