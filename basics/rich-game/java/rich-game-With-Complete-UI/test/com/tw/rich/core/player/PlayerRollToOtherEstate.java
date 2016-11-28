package com.tw.rich.core.player;

import com.tw.rich.core.Dice;
import com.tw.rich.core.Game;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.commands.CommandFactory;
import com.tw.rich.core.places.Estate;
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
public class PlayerRollToOtherEstate {

    public static final int EMPTY_PRICE = 500;
    private Game game;
    private Estate otherEstate;
    private GameMap map;
    private Dice dice;
    private Player otherPlayer;

    @Before
    public void setUp() {
        dice = () -> 1;
        otherPlayer = Player.createPlayerWithFund_Game_Command_State(0, game);
        otherEstate = new Estate(EMPTY_PRICE);
        otherEstate.sellTo(otherPlayer);
        game = mock(Game.class);
        map = mock(GameMap.class);
        when(map.move(any(), anyInt())).thenReturn(otherEstate);
        when(game.getMap()).thenReturn(map);
    }

    @Test
    public void should_bankrupt_if_has_no_enough_money() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
        assertThat(otherEstate.typeFor(player), is(Estate.Type.OTHER));

        player.execute(CommandFactory.Roll(dice));

        assertThat(player.getStatus(), is(Player.Status.BANKRUPT));
    }

    @Test
    public void should_end_turn_if_has_lucky_god() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);
        player.getLuckyGod();

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
        assertThat(otherEstate.typeFor(player), is(Estate.Type.OTHER));

        player.execute(CommandFactory.Roll(dice));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getAsset().getFunds(), is(0));
    }

    @Test
    public void should_end_turn_if_estate_owner_in_hospital_or_prison() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);
        otherPlayer.stuckFor(1);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
        assertThat(otherEstate.typeFor(player), is(Estate.Type.OTHER));

        player.execute(CommandFactory.Roll(dice));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getAsset().getFunds(), is(0));
    }

    @Test
    public void should_charge_if_has_enough_money() {
        Player player = Player.createPlayerWithFund_Game_Command_State(EMPTY_PRICE, game);
        otherEstate.upgrade();

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
        assertThat(player.getAsset().getFunds(), is(EMPTY_PRICE));

        player.execute(CommandFactory.Roll(dice));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getAsset().getFunds(), is(0));
        assertThat(otherPlayer.getAsset().getFunds(), is(EMPTY_PRICE));
    }

}
