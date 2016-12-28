package com.tw.orm;

import com.tw.orm.testObjects.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/28/16.
 */
public class ObjectMapperTest {
    @Test
    public void should_register_and_get_that_object_descriptor() {
        ObjectMapper objectMapper = new AbstractObjectMapper() {};

        ObjectDescriptor objectDescriptor = new ObjectDescriptor(User.class);
        objectMapper.registerObjectDescriptor(objectDescriptor);
        assertThat(objectMapper.getDescriptor(User.class), is(objectDescriptor));
    }
}