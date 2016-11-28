package com.tw.rich.core.player;

import com.tw.rich.core.Dice;
import com.tw.rich.core.Game;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.commands.CommandFactory;
import com.tw.rich.core.places.Prison;
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
public class PlayerRollToPrison {

    public static final int EMPTY_PRICE = 500;
    private Game game;
    private Prison prison;
    private GameMap map;
    private Dice dice;

    @Before
    public void setUp() {
        dice = () -> 1;
        prison = new Prison();
        game = mock(Game.class);
        map = mock(GameMap.class);
        when(map.move(any(), anyInt())).thenReturn(prison);
        when(game.getMap()).thenReturn(map);
    }

    @Test
    public void should_end_turn_and_go_to_prison() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));

        player.execute(CommandFactory.Roll(dice));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.currentPlace(), is(prison));
        assertThat(player.isStucked(), is(true));
    }

    @Test
    public void should_lucky_god_is_valid_for_five_turn() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);
        player.stuckFor(Prison.PRISON_DAYS);
        player.endTurn();

        for(int i = 1; i<= Prison.PRISON_DAYS; i++) {
            player.inTurn();
            assertThat(player.isStucked(), is(true));
            player.endTurn();
        }

        assertThat(player.isStucked(), is(false));
    }

}
