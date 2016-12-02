package com.thoughtworks.mobileCharge.api;

import com.thoughtworks.mobileCharge.domain.user.Balance;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.support.ApiSupport;
import com.thoughtworks.mobileCharge.support.ApiTestRunner;
import com.thoughtworks.mobileCharge.support.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/29/16.
 */
@RunWith(ApiTestRunner.class)
public class UserApiTest extends ApiSupport{

    @Test
    public void should_404_when_get_card_and_card_not_exists() {
        when(userRepo.findBy(anyString())).thenReturn(Optional.empty());
        Response response = get("users/1");
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_404_when_get_card_and_current_user_is_not_uid() {
        User user = TestHelper.getUser(mock(Balance.class));
        User otherUser = TestHelper.getUser(mock(Balance.class));
        when(userRepo.findBy(anyString())).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(otherUser));
        Response response = get("users/"+ user.getId().id());
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_200_when_get_card() {
        User user = TestHelper.getUser(mock(Balance.class));
        when(userRepo.findBy(anyString())).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));

        Response response = get("users/"+ user.getId().id());

        assertThat(response.getStatus(), is(200));
        Map fetchedUserInfo = response.readEntity(Map.class);
        assertThat(fetchedUserInfo.get("id"), is(user.getId().id()));
        assertThat(canFindLink(((List)fetchedUserInfo.get("links")), "self", "users/" + user.getId().id()), is(true));
    }

    @Test
    public void should_404_when_get_balance_and_current_user_is_not_uid() {
        User user = TestHelper.getUser(new Balance());
        User otherUser = TestHelper.getUser(mock(Balance.class));
        when(userRepo.findBy(anyString())).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(otherUser));

        Response response = get("users/"+ user.getId().id() + "/balance");

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_200_when_get_balance_and_current_user_is_not_uid() {
        User user = TestHelper.getUser(new Balance());
        when(userRepo.findBy(anyString())).thenReturn(Optional.of(user));
        when(currentUserService.currentUser()).thenReturn(Optional.of(user));

        Response response = get("users/"+ user.getId().id() + "/balance");

        assertThat(response.getStatus(), is(200));
        Map balanceInfo = response.readEntity(Map.class);
        assertThat(balanceInfo.get("remainedMoney"), is(0.0));
        assertThat(((Map)balanceInfo.get("remainedData")).get("local"), is(0));
        assertThat(((Map)balanceInfo.get("remainedCallMinutes")).get("local"), is(0));
    }
}