package com.tw.orm;

import com.tw.orm.testObjects.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/28/16.
 */
public class ObjectDescriptorTest {
    @Test
    public void should_add_and_get_property_descriptor() {
        ObjectDescriptor objectDescriptor = new ObjectDescriptor(User.class);

        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("username", "username", String.class, false);
        objectDescriptor.addPropertyDescriptor(propertyDescriptor);
        assertThat(objectDescriptor.getPropertyDescriptor("username"), is(propertyDescriptor));
    }
}