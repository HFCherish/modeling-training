package com.thoughtworks.shopping.web;

import com.thoughtworks.shopping.domain.EntityId;
import com.thoughtworks.shopping.domain.products.Product;
import com.thoughtworks.shopping.domain.users.User;
import com.thoughtworks.shopping.support.ApiSupport;
import com.thoughtworks.shopping.support.ApiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.thoughtworks.shopping.support.TestHelper.getProduct;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/23/16.
 */
@RunWith(ApiTestRunner.class)
public class ProductApiTest extends ApiSupport{
    @Test
    public void should_404_if_product_not_exists() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(new EntityId("1"));
        when(productRepo.getProduct(anyString())).thenReturn(Optional.empty());
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUser.get()).thenReturn(user);

        Response response = get("users/" + user.getId().id() + "/products/1");
        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_200_if_get_success() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(new EntityId("1"));
        Product product = getProduct(user);
        when(productRepo.getProduct(anyString())).thenReturn(Optional.of(product));
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(currentUser.get()).thenReturn(user);

        String uri = "users/" + user.getId().id() + "/products/" + product.getId().id();
        Response response = get(uri);
        assertThat(response.getStatus(), is(200));
        Map map = response.readEntity(Map.class);
        assertThat(map.get("id"), is(product.getId().id()));
        assertThat(canFindLink(((List)map.get("links")), "self", uri), is(true));
    }
}