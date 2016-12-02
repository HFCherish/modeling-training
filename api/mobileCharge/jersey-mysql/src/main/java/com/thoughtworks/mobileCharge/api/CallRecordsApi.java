package com.thoughtworks.mobileCharge.api;

import com.thoughtworks.mobileCharge.api.beans.CallRecordRequestBean;
import com.thoughtworks.mobileCharge.api.jersey.Routes;
import com.thoughtworks.mobileCharge.api.services.CallRecordQueryService;
import com.thoughtworks.mobileCharge.domain.Page;
import com.thoughtworks.mobileCharge.domain.user.*;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by pzzheng on 11/29/16.
 */
public class CallRecordsApi {
    private User user;

    public CallRecordsApi(User user) {
        this.user = user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCallRecordUseBean(CallRecordRequestBean info,
                                     @Context UserRepo userRepo,
                                     @Context CallRecordQueryService callRecordQueryService,
                                     @Context Routes routes) {

        CallRecord callerRecord = user.saveCallRecord(new CallRecord(
                info.getFromLocale().getLocale(),
                user,
                new DateTime(info.getDuration().getStart()),
                new Duration(info.getDuration().getStart(), info.getDuration().getEnd()),
                info.getCallType(),
                info.getTarget().getPhoneCard()));

        return Response.status(201).location(routes.callRecordsUrl(user.getId().id(), callerRecord.getId().id())).build();
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<CallRecord> getAllOfUser(@Context CallRecordQueryService callRecordQueryService,
                                         @QueryParam("page") int page,
                                         @QueryParam("perPage") int perPage,
                                         @DefaultValue("0") @QueryParam("month") int month,
                                         @Context UriInfo uriInfo) {
        return callRecordQueryService.findAllOf(user, month).toPage(page, perPage, uriInfo);
    }


}
