package com.tw.rich.core.player;

import com.tw.rich.core.Dice;
import com.tw.rich.core.Game;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.commands.CommandFactory;
import com.tw.rich.core.places.Mineral;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pzzheng on 11/27/16.
 */
public class PlayerRollToMineralEstate {

    public static final int MINERAL_POINTS = 10;
    private Game game;
    private Mineral mineral;
    private GameMap map;
    private Dice dice;

    @Before
    public void setUp() {
        dice = () -> 1;
        mineral = new Mineral(MINERAL_POINTS);
        game = mock(Game.class);
        map = mock(GameMap.class);
        when(map.move(any(), anyInt())).thenReturn(mineral);
        when(game.getMap()).thenReturn(map);
    }

    @Test
    public void should_end_turn_and_go_to_prison() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));

        player.execute(CommandFactory.Roll(dice));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.currentPlace(), is(mineral));
        assertThat(player.getAsset().getPoints(), is(MINERAL_POINTS));
    }

}
