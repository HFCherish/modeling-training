package com.tw.rich.core.map;

import com.tw.rich.core.Game;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.places.Estate;
import com.tw.rich.core.places.Starting;
import com.tw.rich.core.player.Player;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by pzzheng on 11/28/16.
 */
public class GameMapTest {

    @Test
    public void should_able_to_move_to_target() {
        Starting starting = new Starting();
        Estate estate1 = new Estate(10);
        Estate estate2 = new Estate(10);
        Estate estate3 = new Estate(10);
        GameMap map = new GameMap(2, 2, starting, estate1, estate2, estate3);

        assertThat(map.move(starting, 1), is(estate1));
        assertThat(map.move(starting, 5), is(estate1));
        assertThat(map.move(starting, -1), is(estate3));
        assertThat(map.move(starting, -5), is(estate3));
    }

    @Test
    public void should_stop_when_move_if_encounter_block_or_bomb() {
        Starting starting = new Starting();
        Estate estate1 = new Estate(10);
        Estate estate2 = new Estate(10);
        Estate estate3 = new Estate(10);
        GameMap map = new GameMap(2, 2, starting, estate1, estate2, estate3);
        estate1.setTool(Tool.BLOCK);

        assertThat(map.move(starting, 2), is(estate1));
        assertThat(map.move(starting, 4), is(estate1));
        assertThat(map.move(starting, -1), is(estate3));
        assertThat(map.move(starting, -5), is(estate1));
    }

    @Test
    public void should_able_to_set_block_or_bomb() {
        Starting starting = new Starting();
        Estate estate1 = new Estate(10);
        Estate estate2 = new Estate(10);
        Estate estate3 = new Estate(10);
        GameMap map = new GameMap(2, 2, starting, estate1, estate2, estate3);

        assertThat(estate2.getTool(), is(nullValue()));
        assertThat(map.setTool(Tool.BLOCK, starting, 2), is(true));
        assertThat(estate2.getTool(), is(Tool.BLOCK));
    }

    @Test
    public void should_not_set_block_or_bomb_if_condition_unsatisfied() {
        Starting starting = new Starting();
        Estate estate1 = new Estate(10);
        Estate estate2 = new Estate(10);
        Estate estate3 = new Estate(10);
        GameMap map = new GameMap(2, 2, starting, estate1, estate2, estate3);

        assertThat(map.setTool(Tool.BLOCK, starting, 14), is(false));
        assertThat(estate2.getTool(), is(nullValue()));

        Player player = Player.createPlayerWithFund_Game_Command_State(1000, mock(Game.class));
        player.moveTo(estate2);
        map.initPlayers(player);
        assertThat(map.setTool(Tool.BLOCK, starting, 2), is(false));
        assertThat(estate2.getTool(), is(nullValue()));

        estate3.setTool(Tool.BLOCK);
        assertThat(map.setTool(Tool.BLOCK, starting, 3), is(false));
        assertThat(estate3.getTool(), is(Tool.BLOCK));
    }
}