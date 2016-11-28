package com.tw.rich.core.player;

import com.tw.rich.core.Dice;
import com.tw.rich.core.Game;
import com.tw.rich.core.map.GameMap;
import com.tw.rich.core.assistenceItems.Gift;
import com.tw.rich.core.assistenceItems.Tool;
import com.tw.rich.core.commands.CommandFactory;
import com.tw.rich.core.commands.GetGift;
import com.tw.rich.core.places.GiftHouse;
import com.tw.rich.core.places.Hospital;
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
public class PlayerRollToToolTest {

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
    public void should_get_into_hospital_if_encounter_bomb() {
        giftHouse.setTool(Tool.BOMB);
        Hospital hospital = new Hospital();
        when(map.getHospital()).thenReturn(hospital);
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));

        player.execute(CommandFactory.Roll(dice));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player.currentPlace(), is(hospital));
        assertThat(player.isStucked(), is(true));
    }

    @Test
    public void should_get_into_place_after_block_if_encounter_block() {
        giftHouse.setTool(Tool.BLOCK);
        Player player = Player.createPlayerWithFund_Game_Command_State(0, game);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FORM_COMMAND));

        player.execute(CommandFactory.Roll(dice));

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESPONSE));
        assertThat(player.currentPlace(), is(giftHouse));
        assertThat(player.lastCommand instanceof GetGift, is(true));
    }

}
