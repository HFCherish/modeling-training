package com.tw.rich.core.player;

import com.tw.rich.core.Dice;
import com.tw.rich.core.Game;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.commands.CommandFactory;
import com.tw.rich.core.commands.UpgradeEstate;
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
public class PlayerRollToOwnEstate {

    public static final int EMPTY_PRICE = 500;
    private Game game;
    private Estate ownEstate;
    private GameMap map;
    private Dice dice;

    @Before
    public void setUp() {
        dice = () -> 1;
        ownEstate = new Estate(EMPTY_PRICE);
        game = mock(Game.class);
        map = mock(GameMap.class);
        when(map.move(any(), anyInt())).thenReturn(ownEstate);
        when(game.getMap()).thenReturn(map);
    }

    @Test
    public void should_end_turn_if_has_no_enough_money_or_already_highest_level() {
        Player player = Player.createPlayerWithFund_Game_Command_State(EMPTY_PRICE, game);
        ownEstate.sellTo(player);
        ownEstate.upgrade();
        ownEstate.upgrade();
        ownEstate.upgrade();

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
        assertThat(ownEstate.typeFor(player), is(Estate.Type.OWN));

        player.execute(CommandFactory.Roll(dice));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
    }

    @Test
    public void should_upgrade_if_has_enough_money_and_say_yes() {
        Player player = Player.createPlayerWithFund_Game_Command_State(EMPTY_PRICE, game);
        ownEstate.sellTo(player);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));
        assertThat(player.getAsset().getFunds(), is(EMPTY_PRICE));

        player.execute(CommandFactory.Roll(dice));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(player.lastCommand instanceof UpgradeEstate, is(true));

        player.execute(CommandFactory.SayYes);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getAsset().getFunds(), is(0));
        assertThat(ownEstate.getLevel(), is(Estate.Level.THATCH));
    }

}
