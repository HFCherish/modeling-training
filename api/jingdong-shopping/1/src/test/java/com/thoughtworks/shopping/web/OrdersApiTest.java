package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.products.Product;
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

import static com.thoughtworks.shopping.support.TestHelper.getOrder;
import static com.thoughtworks.shopping.support.TestHelper.getProduct;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/23/16.
 */
@RunWith(ApiTestRunner.class)
public class OrdersApiTest extends ApiSupport{
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
    public void should_400_if_no_order_items_when_build_order() {
        Response response = post(ordersUri, new HashMap());
        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_400_if_the_ordered_product_does_not_exists() {
        when(productRepo.getProduct(anyString())).thenReturn(Optional.empty());
        HashMap orderInfo = new HashMap() {{
            put("order_items", asList(
                    new HashMap() {{
                        put("product", "1");
                        put("quantity", 1);
                    }}
            ));
        }};
        Response response = post(ordersUri, orderInfo);

        assertThat(response.getStatus(), is(400));
    }


    @Test
    public void should_201_if_create_order_successfully() {
        final Product product = getProduct(user);
        when(productRepo.getProduct(anyString())).thenReturn(Optional.of(product));
        Order order = getOrder(user, product);
        when(orderRepo.save(anyObject())).thenReturn(order);
        HashMap orderInfo = new HashMap() {{
            put("order_items", asList(
                    new HashMap() {{
                        put("product", product.getId().id());
                        put("quantity", 1);
                    }}
            ));
        }};
        Response response = post(ordersUri, orderInfo);
        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation().toString().contains("/users/" + user.getId().id() + "/orders/" + order.getId().id()), is(true));
    }
}