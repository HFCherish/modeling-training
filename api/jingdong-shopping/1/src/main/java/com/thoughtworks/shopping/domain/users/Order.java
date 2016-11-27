package com.thoughtworks.shopping.domain.users;

import com.thoughtworks.shopping.domain.EntityId;
import com.thoughtworks.shopping.infrastructure.records.Record;
import com.thoughtworks.shopping.util.IdGenerator;
import com.thoughtworks.shopping.web.exception.ConflictException;
import com.thoughtworks.shopping.web.jersey.Routes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/22/16.
 */
public class Order implements Record {
    private User user;
    List<OrderItem> items;
    private EntityId id;
    private Payment payment;


    public Order(User user, List<OrderItem> items) {
        this.user = user;
        this.items = items;
        id = new EntityId(IdGenerator.next());
    }

    public EntityId getId() {
        return id;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("id", id.id());
            put("order_items", items.stream().map(orderItem -> orderItem.toRefJson(routes)));
            put("links", asList(
                    routes.linkMap("self", routes.orderUrl(user.getId().id(), id.id()).getPath())
            ));
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public Payment pay(Map<String, Object> info) {
        return null;
    }

    public User getUser() {
        return user;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void confirmDelivery(Map<String, Object> info) {

    }

    public boolean isPayed() {
        return payment == null;
    }

    public Optional<OrderItem> getItem(String oiid) {
        return null;
    }
}
