package com.thoughtworks.shopping.domain.users;

import com.thoughtworks.shopping.domain.EntityId;
import com.thoughtworks.shopping.domain.products.Product;
import com.thoughtworks.shopping.infrastructure.records.Record;
import com.thoughtworks.shopping.util.IdGenerator;
import com.thoughtworks.shopping.web.ProductApi;
import com.thoughtworks.shopping.web.jersey.Routes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by pzzheng on 11/22/16.
 */
public class User implements Record{
    private final String email;
    private final String password;
    private final EntityId id;
    private final String role;

    public User(String email, String password, UserRole role) {
        id = new EntityId(IdGenerator.next());
        this.email = email;
        this.password = password;
        this.role = role.name();
    }

    public String getRole() {
        return role;
    }

    public EntityId getId() {
        return id;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap(){{
            put("id", id.id());
            put("email", email);
            put("links", Arrays.asList(
                    routes.linkMap("self", routes.userUrl(id.id()).getPath())
            ));
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

}
