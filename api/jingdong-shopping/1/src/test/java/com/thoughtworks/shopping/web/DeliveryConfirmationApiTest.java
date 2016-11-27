package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.EntityId;
import com.thoughtworks.shopping.domain.users.Order;
import com.thoughtworks.shopping.domain.users.User;
import com.thoughtworks.shopping.domain.users.UserRole;
import com.thoughtworks.shopping.support.ApiSupport;
import com.thoughtworks.shopping.support.ApiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/25/16.
 */
@RunWith(ApiTestRunner.class)
public class DeliveryConfirmationApiTest extends ApiSupport{
    private User user;
    private String orderUri;
    private Order order;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        user = new User("cherish@163.com", "123", UserRole.BUYER);
        when(currentUser.get()).thenReturn(user);
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));

        order = mock(Order.class);
        when(order.getId()).thenReturn(new EntityId("1"));
        when(order.getUser()).thenReturn(user);
        when(orderRepo.findById(anyString())).thenReturn(Optional.of(order));
        orderUri = "/users/" + user.getId().id() + "/orders/" + order.getId().id();
    }

    @Test
    public void should_409_if_has_not_pay() {
        when(order.isPayed()).thenReturn(false);
        Response response = post(orderUri + "/delivery_confirmation", new HashMap() {{
            put("confirmation", true);
        }});

        assertThat(response.getStatus(), is(409));
    }

    @Test
    public void should_201_confirm_success() {
        when(order.isPayed()).thenReturn(true);
        Response response = post(orderUri + "/delivery_confirmation", new HashMap() {{
            put("confirmation", true);
        }});

        assertThat(response.getStatus(), is(201));
    }
}