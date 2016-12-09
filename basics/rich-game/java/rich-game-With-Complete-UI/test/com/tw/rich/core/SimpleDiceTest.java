package com.tw.rich.core;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by pzzheng on 12/6/16.
 */
public class SimpleDiceTest {
    @Test
    public void should_get_number_between_1_to_6() {
        Dice dice = SimpleDice.get();

        for(int i=0; i<100; i++) {
            assertThat(dice.next()>0, is(true));
            assertThat(dice.next()<=6, is(true));
        }
    }
}