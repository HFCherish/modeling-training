package com.thoughtworks.mobileCharge.api;

import com.thoughtworks.mobileCharge.domain.EntityId;
import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.domain.user.*;
import com.thoughtworks.mobileCharge.support.ApiSupportWithMock;
import com.thoughtworks.mobileCharge.support.ApiTestRunner;
import com.thoughtworks.mobileCharge.support.TestHelper;
import com.thoughtworks.mobileCharge.util.LocaleFormatter;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.thoughtworks.mobileCharge.support.TestHelper.*;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/29/16.
 */
@RunWith(ApiTestRunner.class)
public class MessageRecordsApiTest extends ApiSupportWithMock {

    public String messageRecordsUrl(User user) {
        return "users/" + user.getId().id() + "/messages";
    }

    @Test
    public void should_400_when_post_message_record_if_input_incomplete() {
        User user = getUser(mock(Balance.class));
        when(userRepo.findById(eq(user.getId().id()))).thenReturn(Optional.of(user));
        when(userRepo.findById(eq("1"))).thenReturn(Optional.empty());
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));

        Response response = post(messageRecordsUrl(user), new HashMap());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_201_when_post_messages() {
        User user = spy(getUser(mock(Balance.class)));
        when(userRepo.findById(eq(user.getId().id()))).thenReturn(Optional.of(user));
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
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(currentUser));

        Response response = post(messageRecordsUrl(user), TestHelper.messageRecordMap());

        assertThat(response.getStatus(), is(404));
    }


    @Test
    public void should_200_when_get_all_call_records() {
        User user = getUser(mock(Balance.class));
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));
        MessageRecord messageRecord = new MessageRecord(user,
                beijingLocale(),
                new PhoneCard("12332432211", beijingLocale()),
                MessageRecord.Type.MMS,
                MessageRecord.SendType.SENDER,
                new DateTime().getMillis());
        when(messageRecordQueryService.findAllOf(eq(user), anyInt())).thenReturn(new PaginatedList<>(1, (page, perPage) -> asList(messageRecord)));


        Response response = get(messageRecordsUrl(user));

        assertThat(response.getStatus(), is(200));
        Map fetchedInfo = response.readEntity(Map.class);
        assertThat(fetchedInfo.get("count"), is(1));
        assertThat(((List) fetchedInfo.get("items")).size(), is(1));
        Map messageRecordInfo = (Map) ((List) fetchedInfo.get("items")).get(0);
        assertThat(messageRecordInfo.get("id"), is(messageRecord.getId().id()));
        assertThat(messageRecordInfo.get("type"), is(MessageRecord.Type.MMS.name()));
        assertThat(messageRecordInfo.get("send_type"), is(MessageRecord.SendType.SENDER.name()));
        assertThat(messageRecordInfo.get("from_locale"), is(LocaleFormatter.getCityAndCountry(beijingLocale())));
        assertThat(messageRecordInfo.get("communication_type"), is(CommunicationRecord.CommunicationType.LOCAL.name()));
        assertThat(messageRecordInfo.get("fee"), is(0.0));
        assertThat(canFindLink((List) messageRecordInfo.get("links"), "self", messageRecordsUrl(user) + "/" + messageRecord.getId().id()), is(true));

    }
}