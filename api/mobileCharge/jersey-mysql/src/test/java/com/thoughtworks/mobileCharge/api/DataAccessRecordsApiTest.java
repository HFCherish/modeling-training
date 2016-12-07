package com.thoughtworks.mobileCharge.api;

import com.thoughtworks.mobileCharge.domain.ChargeType;
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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by pzzheng on 11/29/16.
 */
@RunWith(ApiTestRunner.class)
public class DataAccessRecordsApiTest extends ApiSupportWithMock {

    public String dataAccessRecordsUrl(User user) {
        return "users/" + user.getId().id() + "/data_accesses";
    }

    @Test
    public void should_400_when_post_data_access_record_if_input_incomplete() {
        User user = getUser(mock(Balance.class));
        when(userRepo.findById(eq(user.getId().id()))).thenReturn(Optional.of(user));
        when(userRepo.findById(eq("1"))).thenReturn(Optional.empty());
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));

        Response response = post(dataAccessRecordsUrl(user), new HashMap());

        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_201_when_post_data_access_record() {
        User user = spy(getUser(mock(Balance.class)));
        when(userRepo.findById(eq(user.getId().id()))).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));
        DataAccessRecord dataAccessRecord = mock(DataAccessRecord.class);
        when(dataAccessRecord.getId()).thenReturn(new EntityId("1"));
        when(user.saveDataAccess(anyObject())).thenReturn(dataAccessRecord);

        Response response = post(dataAccessRecordsUrl(user), dataAccessRecordMap());

        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString().contains(dataAccessRecordsUrl(user) + "/" + dataAccessRecord.getId().id()), is(true));
    }

    @Test
    public void should_404_when_post_data_access_record_to_other_card() {
        User user = getUser(mock(Balance.class));
        User currentUser = getUser(mock(Balance.class));
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(currentUser));

        Response response = post(dataAccessRecordsUrl(user), TestHelper.dataAccessRecordMap());

        assertThat(response.getStatus(), is(404));
    }


    @Test
    public void should_200_when_get_all_call_records() {
        User user = getUser(mock(Balance.class));
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));
        DataAccessRecord dataAccessRecord = new DataAccessRecord(user,
                beijingLocale(),
                "163.music.com",
                DataAccessRecord.WebType.G4,
                ChargeType.CHARGE,
                789798l,
                new DateTime().getMillis());
        when(dataAccessRecordQueryService.findAllOf(eq(user), anyInt())).thenReturn(new PaginatedList<>(1, (page, perPage) -> asList(dataAccessRecord)));


        Response response = get(dataAccessRecordsUrl(user));

        assertThat(response.getStatus(), is(200));
        Map fetchedInfo = response.readEntity(Map.class);
        assertThat(fetchedInfo.get("count"), is(1));
        assertThat(((List) fetchedInfo.get("items")).size(), is(1));
        Map messageRecordInfo = (Map) ((List) fetchedInfo.get("items")).get(0);
        assertThat(messageRecordInfo.get("id"), is(dataAccessRecord.getId().id()));
        assertThat(messageRecordInfo.get("web_type"), is(DataAccessRecord.WebType.G4.name()));
        assertThat(messageRecordInfo.get("charge_type"), is(ChargeType.CHARGE.name()));
        assertThat(messageRecordInfo.get("from_locale"), is(LocaleFormatter.getCityAndCountry(beijingLocale())));
        assertThat(messageRecordInfo.get("target_consumer"), is("163.music.com"));
        assertThat(messageRecordInfo.get("communication_type"), is(CommunicationRecord.CommunicationType.LOCAL.name()));
        assertThat(messageRecordInfo.get("fee"), is(0.0));
        assertThat(canFindLink((List) messageRecordInfo.get("links"), "self", dataAccessRecordsUrl(user) + "/" + dataAccessRecord.getId().id()), is(true));

    }
}