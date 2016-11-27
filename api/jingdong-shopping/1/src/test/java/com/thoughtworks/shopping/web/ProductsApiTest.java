package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.EntityId;
import com.thoughtworks.shopping.domain.products.Product;
import com.thoughtworks.shopping.domain.users.User;
import com.thoughtworks.shopping.domain.users.UserRole;
import com.thoughtworks.shopping.support.ApiSupport;
import com.thoughtworks.shopping.support.ApiTestRunner;
import com.thoughtworks.shopping.support.TestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/23/16.
 */
@RunWith(ApiTestRunner.class)
public class ProductsApiTest extends ApiSupport{
    private User user;
    private String productsUrl;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        user = new User("cherish@163.com", "123", UserRole.SELLER);
        when(currentUser.get()).thenReturn(user);
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        productsUrl = "users/" + user.getId().id() + "/products";
    }

    @Test
    public void should_400_if_no_order_items_when_build_order() {
        Response response = post(productsUrl, new HashMap());
        assertThat(response.getStatus(), is(400));
    }

    @Test
    public void should_403_if_current_user_is_not_seller() {
        when(currentUser.get()).thenReturn(new User("cherish@163.com", "123", UserRole.BUYER));
        Response response = post(productsUrl, new HashMap(){{
            put("name", "Holiday");
            put("description", "things can be happy");
        }});
        assertThat(response.getStatus(), is(403));
    }

    @Test
    public void should_201_if_create_product_successfully() {
        User user = mock(User.class);
        when(user.getRole()).thenReturn(UserRole.SELLER.name());
        when(user.getId()).thenReturn(new EntityId("1"));
        Product holiday = TestHelper.getProduct(user);
        when(productRepo.createProduct(anyObject())).thenReturn(holiday);
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUser.get()).thenReturn(user);

        String uri = "users/" + user.getId().id() + "/products";
        Response response =   post(uri, new HashMap(){{
            put("name", "Holiday");
            put("description", "things can be happy");
        }});
        assertThat(response.getStatus(), is(201));
        System.out.println(response.getLocation());
        assertThat(response.getLocation().toString().contains(uri + "/" + holiday.getId().id()), is(true));
    }

}