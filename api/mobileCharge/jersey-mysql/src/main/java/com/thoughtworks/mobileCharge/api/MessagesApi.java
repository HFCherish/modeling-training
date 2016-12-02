package com.thoughtworks.mobileCharge.api;

import com.thoughtworks.mobileCharge.api.beans.MessageRequestBean;
import com.thoughtworks.mobileCharge.api.jersey.Routes;
import com.thoughtworks.mobileCharge.api.services.MessageRecordQueryService;
import com.thoughtworks.mobileCharge.domain.Page;
import com.thoughtworks.mobileCharge.domain.user.MessageRecord;
import com.thoughtworks.mobileCharge.domain.user.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
    public Response createMessage(MessageRequestBean info,
                                  @Context Routes routes) {


        MessageRecord messageRecord = user.saveMessage(new MessageRecord(user,
                info.getFromLocale().getLocale(),
                info.getTarget().getPhoneCard(),
                info.getType(),
                info.getSendType(),
                info.getCreatedAt()));

        return Response.created(routes.messageRecordUrl(user.getId().id(), messageRecord.getId().id())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<MessageRecord> findAll(@DefaultValue("0") @QueryParam("month") int month,
                                       @QueryParam("page") int page,
                                       @QueryParam("perPage") int perPage,
                                       @Context UriInfo uriInfo,
                                       @Context MessageRecordQueryService messageRecordQueryService) {
        return messageRecordQueryService.findAllOf(user, month).toPage(page, perPage, uriInfo);
    }
}
