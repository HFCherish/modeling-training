package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.users.Order;
import com.thoughtworks.shopping.domain.users.User;
import com.thoughtworks.shopping.domain.users.UserRole;
import com.thoughtworks.shopping.support.ApiSupport;
import com.thoughtworks.shopping.support.ApiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;

import static com.thoughtworks.shopping.support.TestHelper.getOrder;
import static com.thoughtworks.shopping.support.TestHelper.getProduct;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/23/16.
 */
@RunWith(ApiTestRunner.class)
public class OrderApiTest extends ApiSupport{
    private User user;
    private String ordersUri;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        user = new User("cherish@163.com", "123", UserRole.BUYER);
        when(currentUser.get()).thenReturn(user);
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        ordersUri = "/users/" + user.getId().id() + "/orders";
    }

    @Test
    public void should_404_if_not_exists_when_get_order() {
        when(orderRepo.findById(anyString())).thenReturn(Optional.empty());
        Response response = get(ordersUri + "/1");

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_200_if_get_successfully() {
        Order order = getOrder(user, getProduct(user));
        when(orderRepo.findById(anyString())).thenReturn(Optional.of(order));
        Response response = get(ordersUri + "/" + order.getId().id());

        assertThat(response.getStatus(), is(200));
        Map map = response.readEntity(Map.class);
        assertThat(map.get("id"), is(order.getId().id()));

    }
}