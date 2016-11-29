package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepo;

import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

import static com.thoughtworks.ketsu.web.validators.Validators.*;

/**
 * Created by pzzheng on 11/29/16.
 */
public class CallsApi {
    private User user;

    public CallsApi(User user) {
        this.user = user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCallRecord(Map<String, Object> info,
                                     @Context AuthorizationService authorizationService,
                                     @Context UserRepo userRepo) {
        if (!authorizationService.currentUserIs(user)) {
            throw new NotFoundException("user not exists");
        }

        validate(all(fieldNotEmpty("from_locale", "from_locale is required"),
                fieldNotEmpty("target", "target id is required"),
                fieldNotEmpty("duration", "duration is required"),
                objectExists("target", "target must be valid", userRepo)), info);
        validate(all(fieldNotEmpty("start", "start time is required"),
                fieldNotEmpty("end", "end time is required")), (Map) info.get("duration"));


        return Response.status(201).build();
    }


}
