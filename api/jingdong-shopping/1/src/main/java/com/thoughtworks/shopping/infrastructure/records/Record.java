package com.thoughtworks.shopping.infrastructure.records;

import com.thoughtworks.shopping.web.jersey.Routes;

import java.util.Map;

public interface Record {
    Map<String, Object> toRefJson(Routes routes);

    Map<String, Object> toJson(Routes routes);
}
