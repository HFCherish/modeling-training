package com.tw.orm;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/28/16.
 */
public class ConversionContextTest {
    @Test
    public void should_return_the_obj_if_convert_to_same_type() {
        ConversionContext conversionContext = new ConversionContext();
        assertThat(conversionContext.convert("string", String.class), is("string"));
        assertThat(conversionContext.convert(1, Integer.class), is(1));
        assertThat(conversionContext.convert(1.1, Double.class), is(1.1));
        assertThat(conversionContext.convert(true, Boolean.class), is(true));
    }
}