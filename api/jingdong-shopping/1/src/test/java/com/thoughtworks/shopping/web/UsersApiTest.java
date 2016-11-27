package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.EntityId;
import com.thoughtworks.shopping.domain.users.User;
import com.thoughtworks.shopping.support.ApiSupport;
import com.thoughtworks.shopping.support.ApiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * Created by pzzheng on 11/22/16.
 */
@RunWith(ApiTestRunner.class)
public class UsersApiTest extends ApiSupport {

    @Override
    @Before
    public void setUp() throws Exception {
        reset(userRepo);
        super.setUp();
    }

    @Test
    public void should_400_if_input_incomplete() {
        User user = mock(User.class);
        when(userRepo.findBy(anyObject())).thenReturn(Optional.of(user));
        HashMap<String, Object> userInfo = userInfo();
        userInfo.remove("password");
        Response response = target("/users").request().post(Entity.json(userInfo));
        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_400_if_email_registered() {
        User user = mock(User.class);
        when(userRepo.findBy(anyObject())).thenReturn(Optional.of(user));

        Response response = target("/users").request().post(Entity.json(userInfo()));
        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_201_when_register_successfully() {
        when(userRepo.findBy(anyObject())).thenReturn(Optional.empty());
        User user = mock(User.class);
        when(user.getId()).thenReturn(new EntityId("1"));
        when(userRepo.save(anyObject())).thenReturn(user);

        Response response = target("/users").request().post(Entity.json(userInfo()));
        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString().contains("/users/1"), is(true));
    }

    private HashMap<String, Object> userInfo() {
        return new HashMap() {{
                put("email", "cherish@163.com");
                put("password", "123");
                put("nickname", "cherish");
            }};
    }
}