package com.thoughtworks.shopping.support;

import com.thoughtworks.shopping.domain.products.Product;
import com.thoughtworks.shopping.domain.users.Order;
import com.thoughtworks.shopping.domain.users.OrderItem;
import com.thoughtworks.shopping.domain.users.User;

import static java.util.Arrays.asList;

public class TestHelper {
    public static Product getProduct(User user) {
        return new Product("holiday", "things can be happy", user);
    }

    public static Order getOrder(User user, Product product) {
        return new Order(user, asList(new OrderItem(product, 1)));
    }
}
