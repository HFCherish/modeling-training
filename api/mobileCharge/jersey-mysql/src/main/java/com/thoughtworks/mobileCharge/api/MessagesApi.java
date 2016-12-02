package com.thoughtworks.mobileCharge.api;

import com.thoughtworks.mobileCharge.api.beans.MessageRequestBean;
import com.thoughtworks.mobileCharge.domain.user.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by pzzheng on 11/29/16.
 */
public class MessagesApi {
    private User user;

    public MessagesApi(User user) {
        this.user = user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMessage(MessageRequestBean info) {
        return Response.created(URI.create("")).build();
    }
}
