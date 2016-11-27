package com.thoughtworks.shopping.domain.users;

import com.thoughtworks.shopping.domain.EntityId;
import com.thoughtworks.shopping.domain.products.Product;
import com.thoughtworks.shopping.infrastructure.records.Record;
import com.thoughtworks.shopping.util.IdGenerator;
import com.thoughtworks.shopping.web.jersey.Routes;

import javax.ws.rs.BadRequestException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/22/16.
 */
public class OrderItem implements Record {
    private final int quantity;
    private final EntityId id;
    private Product product;

    public OrderItem(Product product, int quantity) {
        this.id = new EntityId(IdGenerator.next());
        safeSetProduct(product);
        this.quantity = quantity;
    }

    private void safeSetProduct(Product product) {
        if (product == null)
            throw new BadRequestException("product does not exists");
        this.product = product;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("id", id.id());
            put("product", product.getId().id());
            put("quantity", quantity);
            put("links", asList(
                    routes.linkMap("product", routes.productUrl(product.getOwner().getId().id(), product.getId().id()).getPath())
            ));
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public EntityId getId() {
        return id;
    }

    public void createRefundRequest(Map<String, Object> info) {

    }
}
