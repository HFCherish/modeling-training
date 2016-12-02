package com.thoughtworks.mobileCharge.api;

import com.thoughtworks.mobileCharge.domain.EntityId;
import com.thoughtworks.mobileCharge.domain.user.Balance;
import com.thoughtworks.mobileCharge.domain.user.MessageRecord;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.support.ApiSupport;
import com.thoughtworks.mobileCharge.support.ApiTestRunner;
import com.thoughtworks.mobileCharge.support.TestHelper;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Optional;

import static com.thoughtworks.mobileCharge.support.TestHelper.getUser;
import static com.thoughtworks.mobileCharge.support.TestHelper.messageRecordMap;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/29/16.
 */
@RunWith(ApiTestRunner.class)
public class MessageRecordsApiTest extends ApiSupport {

    public String messageRecordsUrl(User user) {
        return "users/" + user.getId().id() + "/messages";
    }

    @Test
    public void should_400_when_post_message_record_if_input_incomplete() {
        User user = getUser(mock(Balance.class));
        when(userRepo.findBy(eq(user.getId().id()))).thenReturn(Optional.of(user));
        when(userRepo.findBy(eq("1"))).thenReturn(Optional.empty());
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));

        Response response = post(messageRecordsUrl(user), new HashMap());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_201_when_post_messages() {
        User user = spy(getUser(mock(Balance.class)));
        when(userRepo.findBy(eq(user.getId().id()))).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));
        MessageRecord messageRecord = mock(MessageRecord.class);
        when(messageRecord.getId()).thenReturn(new EntityId("1"));
        when(user.saveMessage(anyObject())).thenReturn(messageRecord);

        Response response = post(messageRecordsUrl(user), messageRecordMap());

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString().contains(messageRecordsUrl(user) + "/" + messageRecord.getId().id()), is(true));
    }

    @Test
    public void should_404_when_post_message_record_to_other_card() {
        User user = getUser(mock(Balance.class));
        User currentUser = getUser(mock(Balance.class));
        when(userRepo.findBy(anyString())).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(currentUser));

        Response response = post(messageRecordsUrl(user), TestHelper.messageRecordMap());

        assertThat(response.getStatus(), is(404));
    }

    //
//    @Test
//    public void should_200_when_get_all_call_records() {
//        User user = getUser(mock(Balance.class));
//        when(userRepo.findBy(anyString())).thenReturn(Optional.of(user));
//        when(currentUserService.currentUser()).thenReturn(Optional.of(user));
//        CallRecord callRecord = new CallRecord(user, getUser(mock(Balance.class)), new Locale("zh", "CN", "beijing"), new Duration(1000), CallRecord.CallType.CALLER);
//        when(callRecordRepo.findAllOf(eq(user))).thenReturn(new PaginatedList<>(1, (page, perPage) -> asList(callRecord)));
//
//
//        Response response = get(messageRecordsUrl(user));
//
//        assertThat(response.getStatus(), is(200));
//        Map fetchedInfo = response.readEntity(Map.class);
//        assertThat(fetchedInfo.get("count"), is(1));
//        assertThat(((List) fetchedInfo.get("items")).size(), is(1));
//        Map callRecordInfo = (Map) ((List) fetchedInfo.get("items")).get(0);
//        assertThat(callRecordInfo.get("id"), is(callRecord.getId().id()));
//        assertThat(callRecordInfo.get("call_type"), is(CallRecord.CallType.CALLER.name()));
//        assertThat(callRecordInfo.get("communication_type"), is(CallRecord.CommunicationType.LOCAL.name()));
//        assertThat(callRecordInfo.get("fee"), is(0.0));
//        assertThat(canFindLink((List) callRecordInfo.get("links"), "self", messageRecordsUrl(user) + "/" + callRecord.getId().id()), is(true));
//
//    }
}