package com.tw.rich.io;

import com.tw.rich.core.Game;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by pzzheng on 12/6/16.
 */
public class GameFactoryTest {
    @Test
    public void should_init_game() {
        Game defaultGame = GameFactory.createDefaultGame(1000, "12");

        assertThat(defaultGame.getMap(), is(notNullValue()));
        assertThat(defaultGame.currentPlayer().getIdentity().getId(), is("1"));
    }
}