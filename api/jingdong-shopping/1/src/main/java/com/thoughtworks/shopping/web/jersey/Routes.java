package com.thoughtworks.shopping.web.jersey;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.HashMap;

public class Routes {

    private final String baseUri;

    public Routes(UriInfo uriInfo) {
        baseUri = uriInfo.getBaseUri().toASCIIString();
    }

    public URI userUrl(String uid) {
        return URI.create(baseUri + "users/" + uid);
    }

    public HashMap<String, Object> linkMap(String name, String value) {
        return new HashMap() {{
            put("rel", name);
            put("uri", value);
        }};
    }

    public URI productUrl(String uid, String pid) {
        return URI.create(baseUri + "users/" + uid + "/products/" + pid);
    }

    public URI orderUrl(String uid, String oid) {
        return URI.create(baseUri + "users/" + uid + "/orders/" + oid);
    }
}
