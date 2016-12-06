package com.tw.rich.io;

import com.tw.rich.core.player.Player;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by pzzheng on 12/6/16.
 */
public class PlayersFactoryTest {
    @Test
    public void should_get_players_from_input() {
        List<Player> players = PlayersFactory.getPlayers("12", 1000);

        assertThat(players.size(), is(2));
        assertThat(players.get(0).getIdentity().getId(), is("1"));
        System.out.println(players.get(0).getIdentity().getColor() + players.get(0).getIdentity().getId());
    }

}