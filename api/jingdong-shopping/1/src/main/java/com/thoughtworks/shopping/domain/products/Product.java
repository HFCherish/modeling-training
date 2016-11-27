package com.thoughtworks.shopping.domain.products;

import com.thoughtworks.shopping.domain.EntityId;
import com.thoughtworks.shopping.domain.users.User;
import com.thoughtworks.shopping.infrastructure.records.Record;
import com.thoughtworks.shopping.util.IdGenerator;
import com.thoughtworks.shopping.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/22/16.
 */
public class Product implements Record{
    private final String name;
    private final String description;
    private final EntityId id;
    private final User owner;

    public Product(String name, String description, User owner) {
        this.id = new EntityId(IdGenerator.next());
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    public EntityId getId() {
        return id;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("id", id.id());
            put("name", name);
            put("description", description);
            put("links", asList(
                    routes.linkMap("self", routes.productUrl(owner.getId().id(), id.id()).getPath())
            ));
        }};

    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }


    public User getOwner() {
        return owner;
    }
}
