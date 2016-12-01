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

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createCallRecord(Map<String, Object> info,
//                                     @Context UserRepo userRepo,
//                                     @Context CallRecordRepo callRecordRepo,
//                                     @Context Routes routes) {
//
//
//        validate(all(fieldNotEmpty("from_locale", "from_locale is required"),
//                fieldNotEmpty("target", "target id is required"),
//                fieldNotEmpty("call_type", "call type is required"),
//                fieldNotEmpty("duration", "duration is required")),
//                info);
//        validate(all(fieldNotEmpty("start", "start time is required"),
//                fieldNotEmpty("end", "end time is required")), (Map) info.get("duration"));
//        validate(all(fieldNotEmpty("language", "language is required"),
//                fieldNotEmpty("country", "country is required"),
//                fieldNotEmpty("city", "city is required")), (Map) info.get("from_locale"));
//        long startInstant = (long)(((Map) info.get("duration")).get("start"));
//        long endInstant = (long)(((Map) info.get("duration")).get("end"));
//        Duration duration = new Duration(startInstant, endInstant);
//        Locale from_locale = getLocaleFrom((Map) info.get("from_locale"));
//        Locale target_locale = getLocaleFrom((Map) ((Map) info.get("target")).get("card_locale"));
//
//        CallRecord callerRecord = callRecordRepo.save(new CallRecord(from_locale, user, new DateTime(startInstant), duration, CallRecord.CallType.valueOf(info.get("call_type").toString().toUpperCase()), new PhoneCard(((Map) info.get("target")).get("phone_number").toString(), target_locale)));
//
//        return Response.status(201).location(routes.callRecordsUrl(user.getId().id(), callerRecord.getId().id())).build();
//    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCallRecordUseBean(CallRecordRequestBean info,
                                     @Context UserRepo userRepo,
                                     @Context CallRecordRepo callRecordRepo,
                                     @Context Routes routes) {


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
