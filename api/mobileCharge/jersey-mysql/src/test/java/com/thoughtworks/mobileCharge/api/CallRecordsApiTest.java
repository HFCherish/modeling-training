package com.thoughtworks.mobileCharge.api;

import com.thoughtworks.mobileCharge.domain.EntityId;
import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.domain.user.*;
import com.thoughtworks.mobileCharge.support.ApiSupportWithMock;
import com.thoughtworks.mobileCharge.support.ApiTestRunner;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.*;

import static com.thoughtworks.mobileCharge.support.TestHelper.*;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Created by pzzheng on 11/29/16.
 */
@RunWith(ApiTestRunner.class)
public class CallRecordsApiTest extends ApiSupportWithMock {

    public String callsUrl(User user) {
        return "users/" + user.getId().id() + "/calls";
    }

    @Test
    public void should_201_when_post_calls() {
        User user = spy(getUser(mock(Balance.class)));
        when(userRepo.findById(eq(user.getId().id()))).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));
        CallRecord callRecord = mock(CallRecord.class);
        when(callRecord.getId()).thenReturn(new EntityId("1"));
        doReturn(callRecord).when(user).saveCallRecord(anyObject());
//        when(user.saveDataAccessRecord(callRecord)).thenReturn(callRecord);

        Response response = post(callsUrl(user), new HashMap() {
            {
                put("duration", new HashMap() {{
                    put("start", new DateTime().getMillis());
                    put("end", new DateTime().getMillis());
                }});
                put("from_locale", beijingLocaleMap());
                put("target", phoneCardMap());
                put("call_type", CallRecord.CallType.CALLER);
            }

        });

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString().contains(callsUrl(user) + "/" + callRecord.getId().id()), is(true));
    }

    @Test
    public void should_400_when_post_calls_with_empty_input() {
        User user = getUser(mock(Balance.class));
        when(userRepo.findById(eq(user.getId().id()))).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));

        Response response = post(callsUrl(user), new HashMap());
        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_400_when_post_calls_durantion_end_missed() {
        User user = getUser(mock(Balance.class));
        when(userRepo.findById(eq(user.getId().id()))).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));

        Response response = post(callsUrl(user), new HashMap() {
            {
                put("duration", new HashMap() {{
                    put("start", new DateTime().getMillis());
//                    put("end", new DateTime().getMillis());
                }});
                put("from_locale", beijingLocaleMap());

                put("target", phoneCardMap());
                put("call_type", CallRecord.CallType.CALLER);

            }

        });

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_404_when_post_call_record_to_other_card() {
        User user = getUser(mock(Balance.class));
        User currentUser = getUser(mock(Balance.class));
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(currentUser));

        Response response = post(callsUrl(user), new HashMap());

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_200_when_get_all_call_records() {
        User user = getUser(mock(Balance.class));
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));
        CallRecord callRecord = new CallRecord(new Locale("zh", "CN", "beijing"), user, DateTime.now(), new Duration(1000), CallRecord.CallType.CALLER, new PhoneCard("13245465767", beijingLocale()));
        when(callRecordQueryService.findAllOf(eq(user), anyInt())).thenReturn(new PaginatedList<>(1, (page, perPage) -> asList(callRecord)));


        Response response = get(callsUrl(user));

        assertThat(response.getStatus(), is(200));
        Map fetchedInfo = response.readEntity(Map.class);
        assertThat(fetchedInfo.get("count"), is(1));
        assertThat(((List) fetchedInfo.get("items")).size(), is(1));
        Map callRecordInfo = (Map) ((List) fetchedInfo.get("items")).get(0);
        assertThat(callRecordInfo.get("id"), is(callRecord.getId().id()));
        assertThat(callRecordInfo.get("call_type"), is(CallRecord.CallType.CALLER.name()));
        assertThat(callRecordInfo.get("communication_type"), is(CommunicationRecord.CommunicationType.LOCAL.name()));
        assertThat(callRecordInfo.get("fee"), is(0.0));
        assertThat(canFindLink((List) callRecordInfo.get("links"), "self", callsUrl(user) + "/" + callRecord.getId().id()), is(true));
    }

    @Test
    public void should_get_all_call_records_by_month() {
        User user = getUser(mock(Balance.class));
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));

        CallRecord callRecord_Aug = new CallRecord(new Locale("zh", "CN", "beijing"), user, new DateTime(2016, 8, 1, 1, 1), new Duration(1000), CallRecord.CallType.CALLER, new PhoneCard("13245465767", beijingLocale()));
        CallRecord callRecord_Jul = new CallRecord(new Locale("zh", "CN", "beijing"), user, new DateTime(2016, 7, 1, 1, 1), new Duration(1000), CallRecord.CallType.CALLER, new PhoneCard("13245465767", beijingLocale()));
        when(callRecordQueryService.findAllOf(eq(user), eq(7))).thenReturn(new PaginatedList<>(1, (page, perPage) -> asList(callRecord_Jul)));
        when(callRecordQueryService.findAllOf(eq(user), eq(8))).thenReturn(new PaginatedList<>(1, (page, perPage) -> asList(callRecord_Aug)));
        when(callRecordQueryService.findAllOf(eq(user), eq(0))).thenReturn(new PaginatedList<>(2, (page, perPage) -> asList(callRecord_Aug, callRecord_Jul)));


        Response response = get(callsUrl(user));
        assertThat(((List) response.readEntity(Map.class).get("items")).size(), is(2));

        response = target(callsUrl(user)).queryParam("month", 7).request().get();
        assertThat(((List) response.readEntity(Map.class).get("items")).size(), is(1));

        response = target(callsUrl(user)).queryParam("month", 8).request().get();
        assertThat(((List) response.readEntity(Map.class).get("items")).size(), is(1));
    }
}