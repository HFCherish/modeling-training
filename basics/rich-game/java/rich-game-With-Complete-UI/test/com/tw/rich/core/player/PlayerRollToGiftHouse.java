package com.tw.rich.core.player;

import com.tw.rich.core.Dice;
import com.tw.rich.core.Game;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.assistenceItems.Gift;
import com.tw.rich.core.commands.CommandFactory;
import com.tw.rich.core.commands.GetGift;
import com.tw.rich.core.places.GiftHouse;
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
public class PlayerRollToGiftHouse {

    private Game game;
    private GiftHouse giftHouse;
    private GameMap map;
    private Dice dice;

    @Before
    public void setUp() {
        dice = () -> 1;
        giftHouse = new GiftHouse(Gift.FUND_CARD, Gift.POINT_CARD, Gift.LUCKY_GOD);
        game = mock(Game.class);
        map = mock(GameMap.class);
        when(map.move(any(), anyInt())).thenReturn(giftHouse);
        when(game.getMap()).thenReturn(map);
    }

    @Test
    public void should_get_funds_if_select_fund_card() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));

        player.execute(CommandFactory.Roll(dice));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(player.lastCommand instanceof GetGift, is(true));

        player.execute(CommandFactory.GetGift(Gift.FUND_CARD));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getAsset().getFunds(), is(Gift.FUND_CARD.getValue()));
    }

    @Test
    public void should_get_Points_if_select_point_card() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));

        player.execute(CommandFactory.Roll(dice));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(player.lastCommand instanceof GetGift, is(true));

        player.execute(CommandFactory.GetGift(Gift.POINT_CARD));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.getAsset().getPoints(), is(Gift.POINT_CARD.getValue()));
    }

    @Test
    public void should_get_LuckyGod_if_select_lucky_god() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));

        player.execute(CommandFactory.Roll(dice));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(player.lastCommand instanceof GetGift, is(true));

        player.execute(CommandFactory.GetGift(Gift.LUCKY_GOD));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.isLucky(), is(true));
    }

    @Test
    public void should_lucky_god_is_valid_for_five_turn() {
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);
        player.getLuckyGod();
        player.endTurn();

        for(int i=1; i<=Gift.LUCKY_GOD.getValue(); i++) {
            player.inTurn();
            assertThat(player.isLucky(), is(true));
            player.endTurn();
        }

        assertThat(player.isLucky(), is(false));
    }


}
