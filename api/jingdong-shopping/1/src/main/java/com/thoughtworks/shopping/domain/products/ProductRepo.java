package com.thoughtworks.shopping.domain.products;

import java.util.Optional;

/**
 * Created by pzzheng on 11/22/16.
 */
public interface ProductRepo {
    Product createProduct(Product product);
    Optional<Product> getProduct(String pid);
}
