package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.users.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by pzzheng on 11/22/16.
 */
public class OrderApi {
    private Order order;

    public OrderApi(Order order) {
        this.order = order;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrder() {
        return order;
    }

    @Path("payment")
    public PaymentApi toPaymentApi() {
        return new PaymentApi(order);
    }

    @Path("delivery_confirmation")
    public DeliveryConfirmationApi toDeliveryConfirmationApi() {
        return new DeliveryConfirmationApi(order);
    }

    @Path("order_items/{oiid}/refund_request")
    public RefundRequestApi toRefundRequestApi(@PathParam("oiid") String oiid) {
        return order.getItem(oiid).map(item -> new RefundRequestApi(order, item)).orElseThrow(() -> new NotFoundException("order item not exists"));
    }
}
