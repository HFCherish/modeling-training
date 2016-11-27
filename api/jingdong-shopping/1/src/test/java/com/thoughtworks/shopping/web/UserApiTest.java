package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.EntityId;
import com.thoughtworks.shopping.domain.users.User;
import com.thoughtworks.shopping.domain.users.UserRole;
import com.thoughtworks.shopping.support.ApiSupport;
import com.thoughtworks.shopping.support.ApiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/22/16.
 */
@RunWith(ApiTestRunner.class)
public class UserApiTest extends ApiSupport{

    @Test
    public void should_403_if_see_other_user_profile() {
        User user_current = mock(User.class);
        User user_toAccess = mock(User.class);
        when(user_current.getId()).thenReturn(new EntityId("2"));
        when(user_toAccess.getId()).thenReturn(new EntityId("1"));
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user_toAccess));
        when(currentUser.get()).thenReturn(user_current);

        Response response = get("users/1");
        assertThat(response.getStatus(), is(403));
    }

    @Test
    public void should_404_if_user_not_exists_when_get_user() {
        when(userRepo.findById(anyString())).thenReturn(Optional.empty());

        Response response = get("users/1");
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_200_when_get_user_successfully() {
        User user = getUser();
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUser.get()).thenReturn(user);

        String getOneLink = "users/" + user.getId().id();
        Response response = get(getOneLink);
        assertThat(response.getStatus(), is(200));
        Map fetchedInfo = response.readEntity(Map.class);
        assertThat(fetchedInfo.get("id").toString(), is(user.getId().id()));
        assertThat(canFindLink((List)fetchedInfo.get("links"), "self", getOneLink), is(true));
    }

    @Test
    public void should_400_when_update_given_invalid_input() {
        User user = getUser();
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUser.get()).thenReturn(user);

        String getOneLink = "users/" + user.getId().id();
        Response response = put(getOneLink, new HashMap());
        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_204_when_update_successfully() {
        User user = getUser();
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUser.get()).thenReturn(user);

        String getOneLink = "users/" + user.getId().id();
        Response response = put(getOneLink, new HashMap(){{
            put("password", "234");
        }});
        assertThat(response.getStatus(), is(204));
    }

    private User getUser() {
        return new User("cherish@163.com", "123", UserRole.BUYER);
    }

}