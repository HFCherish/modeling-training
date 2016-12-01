package com.thoughtworks.mobileCharge.infrastructure.records;

import com.thoughtworks.mobileCharge.api.jersey.Routes;

import java.util.Map;

public interface Record {
    Map<String, Object> toRefJson(Routes routes);

    Map<String, Object> toJson(Routes routes);
}
