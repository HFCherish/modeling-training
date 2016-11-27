package com.thoughtworks.shopping.domain.users;

import java.util.Optional;

/**
 * Created by pzzheng on 11/22/16.
 */
public interface OrderRepo {
    Order save(Order order);

    Optional<Order> findById(String oid);
}
