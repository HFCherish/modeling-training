package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.EntityId;
import com.thoughtworks.shopping.domain.users.*;
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
public class RefundRequestApiTest extends ApiSupport {
    private User user;
    private String itemUri;
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
        OrderItem orderItem = mock(OrderItem.class);
        when(orderItem.getId()).thenReturn(new EntityId("1"));
        when(order.getItem(anyString())).thenReturn(Optional.of(orderItem));
        when(orderRepo.findById(anyString())).thenReturn(Optional.of(order));
        itemUri = "/users/" + user.getId().id() + "/orders/" + order.getId().id() + "/order_items/1";
    }

    @Test
    public void should_409_if_has_not_pay() {
        when(order.isPayed()).thenReturn(false);
        Response response = post(itemUri + "/refund_request", new HashMap() {{
            put("reason", "7987");
            put("operation", RefundOperation.REFUND);
            put("quantity", 1);
        }});

        assertThat(response.getStatus(), is(409));
    }

    @Test
    public void should_201_refund_success() {
        when(order.isPayed()).thenReturn(true);
        Response response = post(itemUri + "/refund_request", new HashMap() {{
            put("reason", "7987");
            put("operation", RefundOperation.REFUND);
            put("quantity", 1);
        }});

        assertThat(response.getStatus(), is(201));
    }

}