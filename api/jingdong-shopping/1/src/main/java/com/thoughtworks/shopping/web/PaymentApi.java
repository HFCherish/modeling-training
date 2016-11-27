package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.users.Order;
import com.thoughtworks.shopping.web.jersey.Routes;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

import static com.thoughtworks.shopping.web.validators.Validators.*;

/**
 * Created by pzzheng on 11/22/16.
 */
public class PaymentApi {
    private Order order;

    public PaymentApi(Order order) {
        this.order = order;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pay(Map<String, Object> info,
                        @Context Routes routes) {
        validate(info, all(fieldNotEmpty("account", "account not exists"), fieldNotEmpty("password", "password fail, input again.")));
        order.pay(info);
        return Response.status(201).location(routes.orderUrl(order.getUser().getId().id(), order.getId().id())).build();
    }
}
