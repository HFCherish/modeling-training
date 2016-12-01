package com.thoughtworks.mobileCharge.api;

import com.thoughtworks.mobileCharge.api.beans.CallRecordRequestBean;
import com.thoughtworks.mobileCharge.api.jersey.Routes;
import com.thoughtworks.mobileCharge.domain.Page;
import com.thoughtworks.mobileCharge.domain.user.*;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Locale;
import java.util.Map;

import static com.thoughtworks.mobileCharge.api.validators.Validators.*;
import static com.thoughtworks.mobileCharge.util.LocaleFormatter.getLocaleFrom;

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
                                     @Context CallRecordRepo callRecordRepo,
                                     @Context Routes routes) {
        System.out.println("********* herer");
        Duration duration = new Duration(info.getDuration().getStart(), info.getDuration().getEnd());
        Locale from_locale = getLocaleFrom(info.getFromLocale());
        Locale target_locale = getLocaleFrom(info.getTarget().getCardLocale());

        CallRecord callerRecord = callRecordRepo.save(new CallRecord(from_locale, user, new DateTime(info.getDuration().getStart()), duration, info.getCallType(), new PhoneCard(info.getTarget().getPhoneNumber(), target_locale)));

        return Response.status(201).location(routes.callRecordsUrl(user.getId().id(), callerRecord.getId().id())).build();
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<CallRecord> getAllOfUser(@Context CallRecordRepo callRecordRepo,
                                         @QueryParam("page") int page,
                                         @QueryParam("perPage") int perPage,
                                         @DefaultValue("0") @QueryParam("month") int month,
                                         @Context UriInfo uriInfo) {
        return callRecordRepo.findAllOf(user, month).toPage(page, perPage, uriInfo);
//        return callRecordRepo.findAllOf(user);
    }


}
