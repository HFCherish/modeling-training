package com.thoughtworks.ketsu.web.jersey;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Routes {

    private final String baseUri;

    public Routes(UriInfo uriInfo) {
        baseUri = uriInfo.getBaseUri().toASCIIString();
    }

//    public URI userUrl(User user) {
//        return URI.create(String.format("%susers/%s", baseUri, user.getUserId().id()));
//    }

    public Map<String, Object> linkMap(String rel, String uri) {
        return new HashMap() {{
            put("rel", rel);
            put("uri", uri);
        }};
    }

    public URI userUrl(String uid) {
        return URI.create(String.format("%susers/%s", baseUri, uid));
    }

    public URI callRecordsUrl(String uid, String callRecordId) {
        return URI.create(baseUri + "users/" + uid + "/calls/" + callRecordId);
    }
}
