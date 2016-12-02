package com.thoughtworks.mobileCharge.api;

import com.thoughtworks.mobileCharge.api.beans.DataAccessRequestBean;
import com.thoughtworks.mobileCharge.api.beans.MessageRequestBean;
import com.thoughtworks.mobileCharge.api.jersey.Routes;
import com.thoughtworks.mobileCharge.api.services.DataAccessRecordQueryService;
import com.thoughtworks.mobileCharge.api.services.MessageRecordQueryService;
import com.thoughtworks.mobileCharge.domain.Page;
import com.thoughtworks.mobileCharge.domain.user.DataAccessRecord;
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
public class DataAccessesApi {
    private User user;

    public DataAccessesApi(User user) {
        this.user = user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDataAccess (DataAccessRequestBean info,
                                  @Context Routes routes) {
        DataAccessRecord dataAccessRecord = user.saveDataAccess(new DataAccessRecord(user,
                info.getFromLocale().getLocale(),
                info.getTargetConsumer(),
                info.getWebType(),
                info.getChargeType(),
                info.getData(),
                info.getCreatedAt()));
        return Response.created(routes.dataAccessRecordUrl(user.getId().id(), dataAccessRecord.getId().id())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<DataAccessRecord> findAll(@DefaultValue("0") @QueryParam("month") int month,
                                       @QueryParam("page") int page,
                                       @QueryParam("perPage") int perPage,
                                       @Context UriInfo uriInfo,
                                       @Context DataAccessRecordQueryService dataAccessRecordQueryService) {
        return dataAccessRecordQueryService.findAllOf(user, month).toPage(page, perPage, uriInfo);
    }
}
