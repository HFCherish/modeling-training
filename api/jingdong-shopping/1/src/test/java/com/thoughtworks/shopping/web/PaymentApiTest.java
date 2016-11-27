package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.EntityId;
import com.thoughtworks.shopping.domain.users.Order;
import com.thoughtworks.shopping.domain.users.Payment;
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

import static com.thoughtworks.shopping.support.TestHelper.getOrder;
import static com.thoughtworks.shopping.support.TestHelper.getProduct;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/25/16.
 */
@RunWith(ApiTestRunner.class)
public class PaymentApiTest extends ApiSupport{
    private User user;
    private String orderUri;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        user = new User("cherish@163.com", "123", UserRole.BUYER);
        when(currentUser.get()).thenReturn(user);
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));

        Order order = mock(Order.class);
        when(order.getId()).thenReturn(new EntityId("1"));
        when(order.getUser()).thenReturn(user);
        when(orderRepo.findById(anyString())).thenReturn(Optional.of(order));
        orderUri = "/users/" + user.getId().id() + "/orders/" + order.getId().id();
    }

    @Test
    public void should_400_if_pay_fail() {
        Response response = post(orderUri + "/payment", new HashMap());
        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_201_if_pay_succesfully() {
        HashMap info = new HashMap() {{
            put("account", "5678906789");
            put("password", "123434");
        }};
        Response response = post(orderUri + "/payment", info);
        assertThat(response.getStatus(), is(201));
    }
}